package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.AvailableSeatResponseDto;
import com.scaler.dbmshow.models.ShowSeat;

import java.util.List;

public interface ShowSeatService {
    List<ShowSeat> getAvailableSeats(int showId);
}
