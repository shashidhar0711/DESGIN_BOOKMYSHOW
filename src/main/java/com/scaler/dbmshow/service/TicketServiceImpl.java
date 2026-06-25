package com.scaler.dbmshow.service;

import com.scaler.dbmshow.exceptions.InvalidRequestException;
import com.scaler.dbmshow.exceptions.UnAvailableSeatsException;
import com.scaler.dbmshow.models.*;
import com.scaler.dbmshow.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketServiceImpl implements TicketService{

    private ShowRepository showRepository;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ShowSeatTypeRepository showSeatTypeRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    @org.springframework.context.annotation.Lazy
    private TicketServiceImpl self;

    public TicketServiceImpl(ShowRepository showRepository,
                             ShowSeatRepository showSeatRepository,
                             SeatRepository seatRepository,
                             ShowSeatTypeRepository showSeatTypeRepository,
                             TicketRepository ticketRepository) {
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.seatRepository = seatRepository;
        this.showSeatTypeRepository = showSeatTypeRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket bookTicket(List<Integer> seatIds, int showId, Long userId) {
        // ShowID exist or not
        // userId exist or not
        // validation the start time and end time + 10 minutes before show time
        // to check seatIds are part of show
        // check seats are available status and make them block. use seat lock
        // calculate the price based on seat type
        // create ticket and with status un_paid
        // return it

//        User user = this.userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found!"));



        Show show = this.showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show is not found!"));

        // validation show can be booked?
        Long currentTime = new Date().getTime();
        long tenMinutes = 10 * 60 * 1000;
        if(show.getStartTime().getTime()+tenMinutes < currentTime) {
            throw new RuntimeException("This show cannot be booked. time over!!!");
        }

        // check all the seats part of show
        List<Seat> allSeatsByIdIn = this.seatRepository.findAllByIdIn(seatIds);
        if(allSeatsByIdIn.size() != seatIds.size()) {
            throw new RuntimeException("Seats are invalid");
        }

        // if seats present, now check available and block by applying lock
        self.BlockSeatForUser(seatIds, show, userId);

        List<ShowSeatType> allByShowId = this.showSeatTypeRepository.findAllByShowId(showId);
        Map<SeatType, Double> pricingMap = new HashMap<>();
        for(ShowSeatType showSeatType: allByShowId) {
            SeatType seatType = showSeatType.getSeatType();
            double amount = showSeatType.getAmount();
            pricingMap.put(seatType, amount);
        }

        double totalAmount = 0;
        for(Seat seat: allSeatsByIdIn) {
            totalAmount += pricingMap.get(seat.getSeatType());
        }

        // create a ticket
        Ticket ticket = new Ticket();
        ticket.setTicketStatus(TicketStatus.UNPAID);
        ticket.setUserId(userId);
        ticket.setShow(show);
        ticket.setSeats(allSeatsByIdIn);
        ticket.setTotalAmount(totalAmount);

        Ticket savedToken  = this.ticketRepository.save(ticket);


        // after save, call to payment service by passing ticketid
        //


        return savedToken;
    }

    // critical section or method
    // this method is transactional. it mean i will run all as one or nothing
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void BlockSeatForUser(List<Integer> seatIds, Show show, Long userId) {
//        List<ShowSeat> allByShowIdAndSeatIdsInAndSeatStatusAvailable = this.showSeatRepository.findAllByShowIdAndSeatIdsInAndSeatStatus_Available(show.getId(), seatIds, SeatStatus.AVAILABLE);
//        List<ShowSeat> allByShowIdAndSeatIdsInAndSeatStatusAvailable = this.showSeatRepository.findAllByShow_IdAndSeat_IdInAndSeatStatus(show.getId(), seatIds, SeatStatus.AVAILABLE);

//        List<ShowSeat> allByShowIdAndSeatIdsInAndSeatStatusAvailable = this.showSeatRepository.findAllByShowIdAndSeatIdInAndSeatStatus(show.getId(), seatIds, SeatStatus.AVAILABLE);

        List<ShowSeat> allByShowIdAndSeatIdsInAndSeatStatusAvailable =
                showSeatRepository.findAllByShow_IdAndSeat_IdInAndSeatStatus(
                        show.getId(),
                        seatIds,
                        SeatStatus.AVAILABLE
                );

        if(allByShowIdAndSeatIdsInAndSeatStatusAvailable.size() != seatIds.size()) {
            throw new RuntimeException("Some or all seats are not available");
        }

        allByShowIdAndSeatIdsInAndSeatStatusAvailable.stream().forEach(currSeat -> {
            currSeat.setSeatStatus(SeatStatus.BLOCKED);
            currSeat.setBlockedByUserId(userId);
        });

        this.showSeatRepository.saveAll(allByShowIdAndSeatIdsInAndSeatStatusAvailable);
    }
}
