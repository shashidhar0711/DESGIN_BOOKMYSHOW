package com.scaler.dbmshow.dtos;

import com.scaler.dbmshow.models.SeatType;
import lombok.Data;

@Data
public class PriceConfigDto {
    private SeatType seatType;
    private double price;
}
