package com.scaler.dbmshow.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.dbmshow.models.TicketStatus;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketResponseDto {
    private int ticketId;
    private Double totalAmount;
    private TicketStatus ticketStatus;
    private List<String> seatNames;
    private String errorMessage;
//    private ResponseType responseType;
}
