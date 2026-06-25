package com.scaler.dbmshow.dtos;

import com.scaler.dbmshow.models.TicketStatus;
import lombok.Data;

import java.util.List;

@Data
public class TicketResponseDto {
    private int ticketId;
    private Double totalAmount;
    private TicketStatus ticketStatus;
    private List<String> seatNames;
}
