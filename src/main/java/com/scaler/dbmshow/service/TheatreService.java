package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.TheatreRequestDto;
import com.scaler.dbmshow.exceptions.ResourceNotFoundException;
import com.scaler.dbmshow.models.Theatre;

import java.util.List;

public interface TheatreService {
    Theatre create(TheatreRequestDto request) throws ResourceNotFoundException;

    Theatre getById(int id);

    List<Theatre> getAll();

    Theatre update(int id, TheatreRequestDto request) throws ResourceNotFoundException;

    void delete(int id);
}
