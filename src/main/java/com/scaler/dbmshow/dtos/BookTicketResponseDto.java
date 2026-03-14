package com.scaler.dbmshow.dtos;

import com.scaler.dbmshow.models.Ticket;
import lombok.Data;

@Data
public class BookTicketResponseDto {
    private Ticket ticket;
    private String errorMessage;
    private ResponseType responseType;

}
