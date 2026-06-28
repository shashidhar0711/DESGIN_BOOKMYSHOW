package com.scaler.dbmshow.advice;

import com.scaler.dbmshow.dtos.ExceptionResponseDto;
import com.scaler.dbmshow.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFound(ResourceNotFoundException exception) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
            exceptionResponseDto.setMessage(exception.getMessage());
            exceptionResponseDto.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SeatAlreadyBookedException.class)
    public ResponseEntity<ExceptionResponseDto> handleSeatAlreadyBooked(
            SeatAlreadyBookedException exception) {

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setMessage(exception.getMessage());
        exceptionResponseDto.setStatus(HttpStatus.CONFLICT);

        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionResponseDto> handleInvalidRequest(
            InvalidRequestException exception) {

        ExceptionResponseDto dto = new ExceptionResponseDto();
        dto.setMessage(exception.getMessage());
        dto.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAvailableSeatsException.class)
    public ResponseEntity<ExceptionResponseDto> handleUnavailableSeats(
            UnAvailableSeatsException exception) {

        ExceptionResponseDto dto = new ExceptionResponseDto();
        dto.setMessage(exception.getMessage());
        dto.setStatus(HttpStatus.CONFLICT);

        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ShowCannotBeBookedException.class)
    public ResponseEntity<ExceptionResponseDto> handleShowCannotBeBooked(
            ShowCannotBeBookedException exception) {

        ExceptionResponseDto dto = new ExceptionResponseDto();
        dto.setMessage(exception.getMessage());
        dto.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnableToCreatePaymentLinkException.class)
    public ResponseEntity<ExceptionResponseDto> handleUnableToCreatePaymentLink(
            UnableToCreatePaymentLinkException exception) {

        ExceptionResponseDto dto = new ExceptionResponseDto();
        dto.setMessage(exception.getMessage());
        dto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDto> handleRuntimeException(
            RuntimeException exception) {

        ExceptionResponseDto response = new ExceptionResponseDto();
        response.setMessage(exception.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleException(
            Exception exception) {

        ExceptionResponseDto response = new ExceptionResponseDto();
        response.setMessage("Something went wrong. Please try again later.");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
