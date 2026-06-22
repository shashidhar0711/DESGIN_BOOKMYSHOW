package com.scaler.dbmshow.dtos;

import com.scaler.dbmshow.models.SeatStatus;
import com.scaler.dbmshow.models.SeatType;
import lombok.Data;

@Data
public class AvailableSeatResponseDto {
    private int seatId;
    private Integer showId;
    private String seatName;
    private SeatType seatType;
    private SeatStatus seatStatus;
    private ResponseType responseType;
}




















