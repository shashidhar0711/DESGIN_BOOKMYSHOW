package com.scaler.dbmshow.controller;

import com.scaler.dbmshow.dtos.*;
import com.scaler.dbmshow.exceptions.*;
import com.scaler.dbmshow.models.Seat;
import com.scaler.dbmshow.models.Ticket;
import com.scaler.dbmshow.security.JwtUserDto;
import com.scaler.dbmshow.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public BookTicketResultDto BookTicket(@RequestBody BookTicketRequestDto requestDto,
                                            Authentication authentication) throws InvalidRequestException, UnAvailableSeatsException, SeatAlreadyBookedException, ShowCannotBeBookedException, UnableToCreatePaymentLinkException, ResourceNotFoundException {
            JwtUserDto user = (JwtUserDto) authentication.getPrincipal();
            validateRequest(requestDto);
            BookTicketResultDto result = this.ticketService.bookTicket(
                    requestDto.getSeatIds(),
                    requestDto.getShowId(),
                    user.getUserId()
            );
            result.setResponseType(ResponseType.SUCCESS);
            return result;
    }

    @GetMapping("/internal/{ticketId}")
    public TicketResponseDto getTicketDetails(@PathVariable int ticketId) {
        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        try {
            Ticket ticketDetails = this.ticketService.getTicketDetails(ticketId);
            ticketResponseDto.setTicketId(ticketDetails.getId());
            ticketResponseDto.setTicketStatus(ticketDetails.getTicketStatus());
            ticketResponseDto.setTotalAmount(ticketDetails.getTotalAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ticketResponseDto;
    }

    @PostMapping("/internal/payment-success")
    public ResponseEntity<Void> paymentSuccess(
            @RequestBody PaymentSucessRequestDto requestDto) throws UnAvailableSeatsException {

        ticketService.confirmBooking(
                requestDto.getTicketId()
        );

        return ResponseEntity.ok().build();
    }

    private void validateRequest(BookTicketRequestDto requestDto) throws InvalidRequestException {
        if(requestDto.getSeatIds() == null || requestDto.getSeatIds().isEmpty()){
            throw new InvalidRequestException("SeatIds are invalid!");
        }
        if(requestDto.getShowId() < 0) {
            throw new InvalidRequestException("Show id seems to be invalid");
        }
    }

}


