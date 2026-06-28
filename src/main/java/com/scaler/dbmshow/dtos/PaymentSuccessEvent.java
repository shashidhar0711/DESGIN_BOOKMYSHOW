package com.scaler.dbmshow.dtos;

import lombok.Data;

@Data
public class PaymentSuccessEvent {
    private int ticketId;
    private String razorpayPaymentId;
    private Long amount;
}
