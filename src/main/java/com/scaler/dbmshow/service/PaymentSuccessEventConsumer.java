package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.PaymentSuccessEvent;
import com.scaler.dbmshow.exceptions.UnAvailableSeatsException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentSuccessEventConsumer {
    private final TicketService ticketService;

    public PaymentSuccessEventConsumer(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @KafkaListener(
            topics = "payment_success_topic",
            groupId = "bookmyshow-group"
    )
    public void consume(PaymentSuccessEvent event) throws UnAvailableSeatsException {
        this.ticketService.confirmBooking(event.getTicketId());
    }
}
