package com.scaler.dbmshow.service;

import com.scaler.dbmshow.exceptions.SeatAlreadyBookedException;

import java.util.List;

public interface SeatLockService {
    void lockSeat(int showId, List<Integer> seatIds, int userId) throws SeatAlreadyBookedException;
    boolean unlockSeats(int showId, List<Integer> seatIds);
    boolean isSeatLocked(int showId, int seatIds);

}
