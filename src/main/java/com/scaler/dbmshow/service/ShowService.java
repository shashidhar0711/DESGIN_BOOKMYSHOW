package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.ShowRequestDto;
import com.scaler.dbmshow.exceptions.ResourceNotFoundException;
import com.scaler.dbmshow.models.Show;
import com.scaler.dbmshow.models.ShowSeat;

import java.util.List;

public interface ShowService {
    Show createShow(ShowRequestDto showRequestDto, Long userId) throws ResourceNotFoundException;

    Show getShowById(int showId) throws ResourceNotFoundException;

//    List<Show> getShowsByMovie(Long movieId);
//    List<Show> getShowsByTheatre(Long theatreId);
//    Show updateShow(Long showId, ShowRequestDto requestDto);
//    void deleteShow(Long showId);
}
