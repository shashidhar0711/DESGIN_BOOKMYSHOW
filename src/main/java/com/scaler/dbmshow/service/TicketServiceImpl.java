package com.scaler.dbmshow.service;

import com.scaler.dbmshow.PricingStrategy.PricingStrategy;
import com.scaler.dbmshow.PricingStrategy.PricingStrategyFactory;
import com.scaler.dbmshow.dtos.*;
import com.scaler.dbmshow.exceptions.*;
import com.scaler.dbmshow.models.*;
import com.scaler.dbmshow.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

    private RestTemplate restTemplate;
    private SeatLockService seatLockService;
    private PricingStrategyFactory pricingStrategyFactory;

    @Autowired
    @Lazy
    private TicketServiceImpl self;

    public TicketServiceImpl(ShowRepository showRepository,
                             ShowSeatRepository showSeatRepository,
                             SeatRepository seatRepository,
                             ShowSeatTypeRepository showSeatTypeRepository,
                             TicketRepository ticketRepository,
                             RestTemplate restTemplate,
                             SeatLockService seatLockService,
                             PricingStrategyFactory pricingStrategyFactory) {
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.seatRepository = seatRepository;
        this.showSeatTypeRepository = showSeatTypeRepository;
        this.ticketRepository = ticketRepository;
        this.restTemplate = restTemplate;
        this.seatLockService = seatLockService;
        this.pricingStrategyFactory = pricingStrategyFactory;
    }

    @Override
    public BookTicketResultDto bookTicket(List<Integer> seatIds, int showId, Long userId) throws SeatAlreadyBookedException, ResourceNotFoundException, ShowCannotBeBookedException, UnableToCreatePaymentLinkException, UnAvailableSeatsException {
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
                .orElseThrow(() -> new ResourceNotFoundException("Show is not found!"));

        // validation show can be booked?
        Long currentTime = new Date().getTime();
        long tenMinutes = 10 * 60 * 1000;
        if(show.getStartTime().getTime()+tenMinutes < currentTime) {
            throw new ShowCannotBeBookedException("This show cannot be booked. time over!!!");
        }
        // check all the seats part of show
        List<Seat> allSeatsByIdIn = this.seatRepository.findAllByIdIn(seatIds);
        if(allSeatsByIdIn.size() != seatIds.size()) {
            throw new UnAvailableSeatsException("Seats are not available, seems invalid!");
        }

//        // if seats present, now check available and block by applying lock
//        self.BlockSeatForUser(seatIds, show, userId);

        // Lock Seats using Redis
        this.seatLockService.lockSeat(showId, seatIds, userId.intValue());

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

        // apply strategy here
        PricingResultFinalDto pricing = new PricingResultFinalDto();
        pricing.setBaseAmount(totalAmount);
        pricing.setTotalAmount(totalAmount);

        for (PricingStrategy strategy : pricingStrategyFactory.getStrategies()) {
            strategy.apply(pricing);
        }

        // create a ticket
        Ticket ticket = new Ticket();
        ticket.setTicketStatus(TicketStatus.UNPAID);
        ticket.setUserId(userId);
        ticket.setShow(show);
        ticket.setSeats(allSeatsByIdIn);
        ticket.setTotalAmount(pricing.getTotalAmount());

        Ticket savedTicket  = this.ticketRepository.save(ticket);

        CreatePaymentResponseDto paymentResponseDto;
        try {
            // after save, call to payment service by passing ticketid
            paymentResponseDto = createPayment(savedTicket);
        } catch (Exception e) {

            // Release Redis Lock
            seatLockService.unlockSeats(showId, seatIds);

            // Optional: delete the ticket
            this.ticketRepository.delete(savedTicket);

            throw new UnableToCreatePaymentLinkException("Unable to create payment link.");
        }

        // create response dto
        BookTicketResultDto resultDto = new BookTicketResultDto();

        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ticketResponseDto.setTicketId(savedTicket.getId());
        ticketResponseDto.setTotalAmount(savedTicket.getTotalAmount());
        ticketResponseDto.setTicketStatus(savedTicket.getTicketStatus());
        List<String> seatNames = savedTicket.getSeats()
                        .stream()
                        .map(Seat::getName)
                        .toList();
        ticketResponseDto.setSeatNames(seatNames);

        resultDto.setTicket(ticketResponseDto);
        resultDto.setPayment(paymentResponseDto);

        return resultDto;
    }

    @Override
    public Ticket getTicketDetails(int ticketId) throws ResourceNotFoundException {
        return this.ticketRepository.findById(ticketId).orElseThrow(
                ()-> new ResourceNotFoundException("Ticket:"+ticketId+" is not available!"));
    }

    @Override
    @Transactional
    public void confirmBooking(int ticketId) throws UnAvailableSeatsException {
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow();
        ticket.setTicketStatus(TicketStatus.PAID);

        List<Integer> seatIds = ticket.getSeats()
                .stream()
                .map(Seat::getId)
                .toList();

        List<ShowSeat> showSeats =
                showSeatRepository.findAllByShow_IdAndSeat_IdInAndSeatStatus(
                        ticket.getShow().getId(),
                        seatIds,
                        SeatStatus.AVAILABLE
                );

        if(showSeats.size() != seatIds.size()) {
            throw new UnAvailableSeatsException("Some seats are not blocked");
        }

        showSeats.forEach(showSeat -> {
            showSeat.setSeatStatus(SeatStatus.BOOKED);
        });

        showSeatRepository.saveAll(showSeats);
        ticketRepository.save(ticket);
        // Remove Redis Lock
        seatLockService.unlockSeats(
                ticket.getShow().getId(),
                seatIds
        );

    }

    private CreatePaymentResponseDto createPayment(Ticket ticket) {
        CreatePaymentRequestDto request = new CreatePaymentRequestDto();
        request.setTicketId(ticket.getId());

        CreatePaymentResponseDto createPaymentResponseDto = this.restTemplate.postForObject(
                "http://localhost:8082/payments/",
                request,
                CreatePaymentResponseDto.class
        );

        return createPaymentResponseDto;
    }

    // critical section or method
    // this method is transactional. it mean i will run all as one or nothing
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void BlockSeatForUser(List<Integer> seatIds, Show show, Long userId) throws UnAvailableSeatsException {
        List<ShowSeat> allByShowIdAndSeatIdsInAndSeatStatusAvailable =
                showSeatRepository.findAllByShow_IdAndSeat_IdInAndSeatStatus(
                        show.getId(),
                        seatIds,
                        SeatStatus.AVAILABLE
                );

        if(allByShowIdAndSeatIdsInAndSeatStatusAvailable.size() != seatIds.size()) {
            throw new UnAvailableSeatsException("Some or all seats are not available");
        }

        allByShowIdAndSeatIdsInAndSeatStatusAvailable.stream().forEach(currSeat -> {
            currSeat.setSeatStatus(SeatStatus.BLOCKED);
            currSeat.setBlockedByUserId(userId);
        });

        this.showSeatRepository.saveAll(allByShowIdAndSeatIdsInAndSeatStatusAvailable);
    }
}
