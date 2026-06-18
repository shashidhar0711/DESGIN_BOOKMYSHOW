package com.scaler.dbmshow.dtos;

import com.scaler.dbmshow.models.Show;
import lombok.Data;
import org.springframework.web.client.RestClient;

@Data
public class ShowResponseDto {
    private String errorMessage;
    private ResponseType responseType;
    private Show show;
}
