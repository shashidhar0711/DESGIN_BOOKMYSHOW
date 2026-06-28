package com.scaler.dbmshow.dtos;

import com.scaler.dbmshow.models.Show;
import lombok.Data;
import org.springframework.web.client.RestClient;

import java.util.Date;

@Data
public class ShowResponseDto {
    private String errorMessage;
    private ResponseType responseType;

    private int showId;

    private int movieId;
    private String movieName;

    private int screenId;
    private String screenName;

    private int theatreId;
    private String theatreName;

    private Date startTime;
    private Date endTime;
//    private String errorMessage;
//    private ResponseType responseType;
//    private Show show;
}
