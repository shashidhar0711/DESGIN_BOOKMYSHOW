package com.scaler.dbmshow.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scaler.dbmshow.models.SeatType;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.Date;
import java.util.List;

@Data
public class ShowRequestDto { //
    private int movieId;
    private int screenId;
//    private int userId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date endTime;
    private List<PriceConfigDto> priceConfig;
}
