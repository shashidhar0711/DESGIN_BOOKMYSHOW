package com.scaler.dbmshow.controller;

import com.scaler.dbmshow.dtos.SeatRequestDto;
import com.scaler.dbmshow.dtos.SeatResponseDto;
import com.scaler.dbmshow.models.Seat;
import com.scaler.dbmshow.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public SeatResponseDto createSeat(@RequestBody SeatRequestDto request) {
        try {
            Seat seat = seatService.createSeat(request);
            return SeatResponseDto.from(seat);
        } catch (Exception e) {
            return SeatResponseDto.failure(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public SeatResponseDto getSeatById(@PathVariable int id) {
        try {
            Seat seat = seatService.getSeatById(id);
            return SeatResponseDto.from(seat);
        } catch (Exception e) {
            return SeatResponseDto.failure(e.getMessage());
        }
    }

    @GetMapping
    public List<SeatResponseDto> getAllSeats() {
        List<Seat> seats = seatService.getAllSeats();
        List<SeatResponseDto> response = new ArrayList<>();

        for (Seat seat : seats) {
            response.add(SeatResponseDto.from(seat));
        }
        return response;
    }

    @PutMapping("/{id}")
    public SeatResponseDto updateSeat(@PathVariable int id, @RequestBody SeatRequestDto request) {
        try {
            Seat seat = seatService.updateSeat(id, request);
            return SeatResponseDto.from(seat);
        } catch (Exception e) {
            return SeatResponseDto.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public String deleteSeat(@PathVariable int id) {
        try {
            seatService.deleteSeat(id);
            return "Seat deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
