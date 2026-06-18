package com.scaler.dbmshow.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.dbmshow.models.Seat;
import com.scaler.dbmshow.models.SeatType;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SeatResponseDto {
    private int id;
    private String Name;
    private SeatType seatType;
    private int screenId;
    private String errorMessage;
    private ResponseType responseType;

    public static SeatResponseDto from(Seat seat) {
        SeatResponseDto dto = new SeatResponseDto();
        dto.setId(seat.getId());
        dto.setName(seat.getName());
        dto.setSeatType(seat.getSeatType());
        dto.setScreenId(seat.getScreen().getId());
        dto.setResponseType(ResponseType.SUCCESS);
        return dto;
    }

    public static SeatResponseDto failure(String message) {
        SeatResponseDto dto = new SeatResponseDto();
        dto.setErrorMessage(message);
        dto.setResponseType(ResponseType.FAILURE);
        return dto;
    }
}
