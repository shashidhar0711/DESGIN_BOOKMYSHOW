package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.MovieRequestDto;
import com.scaler.dbmshow.dtos.SeatRequestDto;
import com.scaler.dbmshow.exceptions.ResourceNotFoundException;
import com.scaler.dbmshow.models.Movie;
import com.scaler.dbmshow.models.Seat;

import java.util.List;

public interface SeatService {
    Seat getSeatById(int id) throws ResourceNotFoundException;

    List<Seat> getAllSeats();

    Seat createSeat(SeatRequestDto seatRequestDto);

    void deleteSeat(int id) throws ResourceNotFoundException;

    Seat updateSeat(int id, SeatRequestDto seatRequestDto) throws ResourceNotFoundException;
}
