package com.scaler.dbmshow.dtos;

import com.scaler.dbmshow.models.SeatType;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;

@Data
public class ShowRequestDto {
    private int movieId;
    private int screenId;
    private int userId;
    private Date startTime;
    private Date endTime;
    private List<Pair<SeatType, Double>> priceConfig;
}