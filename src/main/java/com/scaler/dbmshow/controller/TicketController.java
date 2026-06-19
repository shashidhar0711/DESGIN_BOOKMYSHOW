package com.scaler.dbmshow.controller;

import com.scaler.dbmshow.dtos.BookTicketRequestDto;
import com.scaler.dbmshow.dtos.BookTicketResponseDto;
import com.scaler.dbmshow.dtos.ResponseType;
import com.scaler.dbmshow.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public BookTicketResponseDto BookTicket(BookTicketRequestDto requestDto) {
        BookTicketResponseDto responseDto = new BookTicketResponseDto();
        try{
            validateRequest(requestDto);
            this.ticketService.bookTicket(requestDto.getSeatIds(), requestDto.getShowId(), requestDto.getUserId());

        } catch (Exception e){
            responseDto.setResponseType(ResponseType.FAILURE);
            responseDto.setErrorMessage(e.getMessage());
        }
        return responseDto;
    }

    private void validateRequest(BookTicketRequestDto requestDto) {
        if(requestDto.getSeatIds() == null || requestDto.getSeatIds().isEmpty()){
            throw new RuntimeException("Seatids should be present");
        }
        if(requestDto.getUserId() < 0){
            throw new RuntimeException("User id seems to be invalid");
        }
        if(requestDto.getShowId() < 0) {
            throw new RuntimeException("Show id seems to be invalid");
        }
    }
}
