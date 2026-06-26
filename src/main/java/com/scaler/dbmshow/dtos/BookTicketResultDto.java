package com.scaler.dbmshow.dtos;


import lombok.Data;

@Data
public class BookTicketResultDto {
    private TicketResponseDto ticket;
    private CreatePaymentResponseDto payment;
    private ResponseType responseType;
//    private String ErrorMessage;
}
