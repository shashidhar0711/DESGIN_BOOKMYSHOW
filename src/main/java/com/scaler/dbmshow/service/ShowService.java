package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.ShowRequestDto;
import com.scaler.dbmshow.models.Show;

import java.util.List;

public interface ShowService {
    Show createShow(ShowRequestDto showRequestDto);
//    List<Show> getAllShowsByScreen(int screenId);
//    List<Show> getAllShowsByTheatre(int theaterId);
}
