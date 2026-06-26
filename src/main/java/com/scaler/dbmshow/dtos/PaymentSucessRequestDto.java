package com.scaler.dbmshow.dtos;

import lombok.Data;

@Data
public class PaymentSucessRequestDto {
    private int ticketId;
    private int paymentId;
}
