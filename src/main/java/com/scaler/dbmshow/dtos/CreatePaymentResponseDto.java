package com.scaler.dbmshow.dtos;

import lombok.Data;

@Data
public class CreatePaymentResponseDto {
    private int paymentId;
    private String paymentLink;
}
