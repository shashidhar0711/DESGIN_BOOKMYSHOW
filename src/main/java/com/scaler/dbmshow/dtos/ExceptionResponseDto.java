package com.scaler.dbmshow.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ExceptionResponseDto {
    private String message;
    private HttpStatus status;
//    private LocalDateTime timestamp;
}
