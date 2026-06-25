package com.scaler.dbmshow.controller;

import com.scaler.dbmshow.dtos.BookTicketRequestDto;
import com.scaler.dbmshow.dtos.BookTicketResponseDto;
import com.scaler.dbmshow.dtos.ResponseType;
import com.scaler.dbmshow.dtos.TicketResponseDto;
import com.scaler.dbmshow.models.Seat;
import com.scaler.dbmshow.models.Ticket;
import com.scaler.dbmshow.security.JwtUserDto;
import com.scaler.dbmshow.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public BookTicketResponseDto BookTicket(@RequestBody BookTicketRequestDto requestDto,
                                            Authentication authentication) {

        BookTicketResponseDto responseDto = new BookTicketResponseDto();

        try {
            JwtUserDto user = (JwtUserDto) authentication.getPrincipal();

            validateRequest(requestDto);

            Ticket ticket = this.ticketService.bookTicket(
                    requestDto.getSeatIds(),
                    requestDto.getShowId(),
                    user.getUserId()
            );

            TicketResponseDto ticketResponseDto = new TicketResponseDto();
            ticketResponseDto.setTicketId(ticket.getId());
            ticketResponseDto.setTotalAmount(ticket.getTotalAmount());
            ticketResponseDto.setTicketStatus(ticket.getTicketStatus());

            List<String> seatNames = ticket.getSeats()
                    .stream()
                    .map(Seat::getName)
                    .toList();

            ticketResponseDto.setSeatNames(seatNames);

            responseDto.setTicket(ticketResponseDto);
            responseDto.setResponseType(ResponseType.SUCCESS);

        } catch (Exception e) {
            responseDto.setResponseType(ResponseType.FAILURE);
            responseDto.setErrorMessage(e.getMessage());
        }

        return responseDto;
    }

    private void validateRequest(BookTicketRequestDto requestDto) {
        if(requestDto.getSeatIds() == null || requestDto.getSeatIds().isEmpty()){
            throw new RuntimeException("Seatids should be present");
        }
//        if(requestDto.getUserId() < 0){
//            throw new RuntimeException("User id seems to be invalid");
//        }
        if(requestDto.getShowId() < 0) {
            throw new RuntimeException("Show id seems to be invalid");
        }
    }
}
