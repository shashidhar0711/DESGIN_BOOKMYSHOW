package com.scaler.dbmshow.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.dbmshow.models.Ticket;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookTicketResponseDto {
    private Ticket ticket;
    private String errorMessage;
    private ResponseType responseType;

}
