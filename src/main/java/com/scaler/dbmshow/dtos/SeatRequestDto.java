package com.scaler.dbmshow.dtos;

import com.scaler.dbmshow.models.SeatType;
import lombok.Data;

@Data
public class SeatRequestDto {
    private String name;
    private int screenId;
    private SeatType seatType;
}
