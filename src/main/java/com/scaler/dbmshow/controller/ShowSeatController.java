package com.scaler.dbmshow.controller;

import com.scaler.dbmshow.dtos.AvailableSeatResponseDto;
import com.scaler.dbmshow.dtos.ResponseType;
import com.scaler.dbmshow.models.ShowSeat;
import com.scaler.dbmshow.service.ShowSeatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/show-seats")
public class ShowSeatController {
    private final ShowSeatService showSeatService;

    public ShowSeatController(ShowSeatService showSeatService) {
        this.showSeatService = showSeatService;
    }

    @GetMapping("/{showId}/available-seats")
    public List<AvailableSeatResponseDto> getAvailableSeats(@PathVariable int showId) {

            List<ShowSeat> availableSeats = showSeatService.getAvailableSeats(showId);
            List<AvailableSeatResponseDto> responseDtos = new ArrayList<>();

            for(ShowSeat showSeat: availableSeats) {
                AvailableSeatResponseDto response = new AvailableSeatResponseDto();

                response.setSeatId(showSeat.getId());
                response.setShowId(showSeat.getShow().getId());
                response.setSeatName(showSeat.getSeat().getName());
                response.setSeatType(showSeat.getSeat().getSeatType());
                response.setSeatStatus(showSeat.getSeatStatus());
                response.setResponseType(ResponseType.SUCCESS);

                responseDtos.add(response);
            }

        return responseDtos;
    }
}
