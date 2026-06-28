package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.SeatRequestDto;
import com.scaler.dbmshow.exceptions.ResourceNotFoundException;
import com.scaler.dbmshow.models.Movie;
import com.scaler.dbmshow.models.Screen;
import com.scaler.dbmshow.models.Seat;
import com.scaler.dbmshow.repositories.ScreenRepository;
import com.scaler.dbmshow.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService{

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ScreenRepository screenRepository;

    public SeatServiceImpl(SeatRepository seatRepository, ScreenRepository screenRepository) {
        this.seatRepository = seatRepository;
        this.screenRepository = screenRepository;
    }

    @Override
    public Seat getSeatById(int id) throws ResourceNotFoundException {
        return this.seatRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Seat not found!"));
    }

    @Override
    public List<Seat> getAllSeats() {
        return this.seatRepository.findAll();
    }

    @Override
    public Seat createSeat(SeatRequestDto seatRequestDto) {
        Seat seat = new Seat();
        seat.setName(seatRequestDto.getName());
        seat.setSeatType(seatRequestDto.getSeatType());

        Screen screen = this.screenRepository.findById(seatRequestDto.getScreenId())
                .orElseThrow(()-> new RuntimeException("Screen not found!"));

        seat.setScreen(screen);

        return this.seatRepository.save(seat);
    }

    @Override
    public void deleteSeat(int id) throws ResourceNotFoundException {
        Seat seatById = getSeatById(id);
        this.seatRepository.delete(seatById);
    }

    @Override
    public Seat updateSeat(int id, SeatRequestDto seatRequestDto) throws ResourceNotFoundException {
        Seat seatById = getSeatById(id);
        seatById.setName(seatRequestDto.getName());
        seatById.setSeatType(seatRequestDto.getSeatType());

        Screen screen = this.screenRepository.findById(seatRequestDto.getScreenId())
                .orElseThrow(()-> new RuntimeException("Screen not found"));

        seatById.setScreen(screen);
        return this.seatRepository.save(seatById);
    }
}
