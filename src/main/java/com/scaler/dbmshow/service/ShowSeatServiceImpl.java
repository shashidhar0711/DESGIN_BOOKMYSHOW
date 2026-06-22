package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.AvailableSeatResponseDto;
import com.scaler.dbmshow.models.SeatStatus;
import com.scaler.dbmshow.models.ShowSeat;
import com.scaler.dbmshow.repositories.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowSeatServiceImpl implements ShowSeatService{
    public ShowSeatServiceImpl(ShowSeatRepository showSeatRepository) {
        this.showSeatRepository = showSeatRepository;
    }

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Override
    public List<ShowSeat> getAvailableSeats(int showId) {
        List<ShowSeat> availableSeats = this.showSeatRepository.findAllByShow_IdAndSeatStatus(showId, SeatStatus.AVAILABLE);
        return availableSeats;

    }
}
