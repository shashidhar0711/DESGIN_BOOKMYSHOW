package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.MovieRequestDto;
import com.scaler.dbmshow.dtos.MovieResponseDto;
import com.scaler.dbmshow.dtos.ScreenRequestDto;
import com.scaler.dbmshow.dtos.TheatreRequestDto;
import com.scaler.dbmshow.exceptions.ResourceNotFoundException;
import com.scaler.dbmshow.models.Movie;
import com.scaler.dbmshow.models.Screen;
import com.scaler.dbmshow.models.Theatre;

import java.util.List;

public interface ScreenService {
    Screen create(ScreenRequestDto request) throws ResourceNotFoundException;

    Screen getById(int id) throws ResourceNotFoundException;

    List<Screen> getAll();

    Screen update(int id, ScreenRequestDto request) throws ResourceNotFoundException;

    void delete(int id) throws ResourceNotFoundException;
}
