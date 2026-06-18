//package com.scaler.dbmshow.service;
//
//import com.scaler.dbmshow.exceptions.InvalidRequestException;
//import com.scaler.dbmshow.exceptions.UnAvailableSeatsException;
//import com.scaler.dbmshow.models.*;
//import com.scaler.dbmshow.repositories.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Isolation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.*;
//
//@Service
//public class TicketServiceImpl implements TicketService {
//
//    private UserRepository userRepository;
//    private ShowRepository showRepository;
//    private SeatRepository seatRepository;
//    private ShowSeatRepository showSeatRepository;
//    private TicketRepository ticketRepository;
//    private ShowSeatTypeRepository showSeatTypeRepository;
//
//    @Autowired
//    public TicketServiceImpl(UserRepository userRepository, ShowRepository showRepository, SeatRepository seatRepository, ShowSeatRepository showSeatRepository, TicketRepository ticketRepository, ShowSeatTypeRepository showSeatTypeRepository) {
//        this.userRepository = userRepository;
//        this.showRepository = showRepository;
//        this.seatRepository = seatRepository;
//        this.showSeatRepository = showSeatRepository;
//        this.ticketRepository = ticketRepository;
//        this.showSeatTypeRepository = showSeatTypeRepository;
//    }
//
//    @Override
//    public Ticket bookTicket(List<Integer> seatIds, int showId, int userId) throws InvalidRequestException, UnAvailableSeatsException {
//
//        /*
//        validate if the user is a valid user - check if user is present in the db
//        validate if the show is present in the db or not
//        validate if the show can be booked now? start time + 10min is allowed
//        validate if the seats are valid - to check if seats are present in the db
//        fetch all the availabel show<->seats
//        if avl seats count == request seat cound
//          update the seat status blocked
//        else
//          throw exception saying few or all seats are unavailable
//         */
//        Optional<User> userById = this.userRepository.findUserById(userId);
//        if(userById.isEmpty()) {
//            throw new InvalidRequestException("User is invalid!!");
//        }
//        User user = userById.get();
//        Show show = this.showRepository.findById(showId).orElseThrow(() -> new InvalidRequestException("Show is Invalid!!"));
//
//        long currentDateTime = new Date().getTime();
//        if(show.getStartTime().getTime() + (10 * 60L) < currentDateTime) {
//            throw new InvalidRequestException("This show cannot be booked");
//        }
//
//        List<Seat> seats = this.seatRepository.findAllByIdIn(seatIds);
//        if(seats.size() != seatIds.size()) {
//            throw new InvalidRequestException("Seats are Invalid!");
//        }
//        // TODO: check if the seat belong to the which screen on which the show is going to be run
//        BlockSeatForUser(user, show, seatIds);
//
//        List<ShowSeatType> showSeatTypes = this.showSeatTypeRepository.findAllByShow(showId);
//        Map<SeatType, Double> pricingMap = new HashMap<>();
//
//        for(ShowSeatType seatType: showSeatTypes) {
//            pricingMap.put(seatType.getSeatType(), seatType.getAmount());
//        }
//        double totalAmount = 0;
//
//        for(Seat seat: seats) {
//            totalAmount += pricingMap.get(seat.getSeatType());
//        }
//
//        // TODO: Apply strategy design pattern to compute convenience fee and add it to the total amount
//
//
//        Ticket ticket = new Ticket();
//        ticket.setTicketStatus(TicketStatus.UNPAID);
//        ticket.setUser(user);
//        ticket.setShow(show);
//        ticket.setSeats(seats);
//        ticket.setTotalAmount(totalAmount);
//
//        this.ticketRepository.save(ticket);
//
//        return ticket;
//    }
//
//    @Transactional(isolation = Isolation.SERIALIZABLE)
//    public void BlockSeatForUser(User user, Show show, List<Integer> seatIds) throws UnAvailableSeatsException {
//        List<ShowSeat> showSeats = this.showSeatRepository.findAllByShowIdAndSeatIdsInAndSeatStatus_Available(show.getId(), seatIds);
//        if(showSeats.size() != seatIds.size()) {
//            throw new UnAvailableSeatsException("Some or all seat are unavailable");
//        }
//
//        showSeats.stream().forEach(ss -> {
//            ss.setSeatStatus(SeatStatus.BLOCKED);
//            ss.setUser(user);
//        });
//
//        // if the objects that we are trying to store having ids (it means that these )
//        // it means that these object already present in the DB
//        // hence saveAll will fire an update
//        // else saveAll leads to insert query
//        showSeatRepository.saveAll(showSeats);
//    }
//}
