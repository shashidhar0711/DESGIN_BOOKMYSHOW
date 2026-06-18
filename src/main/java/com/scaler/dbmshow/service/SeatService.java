package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.MovieRequestDto;
import com.scaler.dbmshow.dtos.SeatRequestDto;
import com.scaler.dbmshow.models.Movie;
import com.scaler.dbmshow.models.Seat;

import java.util.List;

public interface SeatService {
    Seat getSeatById(int id);

    List<Seat> getAllSeats();

    Seat createSeat(SeatRequestDto seatRequestDto);

    void deleteSeat(int id);

    Seat updateSeat(int id, SeatRequestDto seatRequestDto);
}
