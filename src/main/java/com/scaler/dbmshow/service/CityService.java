package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.CityRequestDto;
import com.scaler.dbmshow.exceptions.ResourceNotFoundException;
import com.scaler.dbmshow.models.City;

import java.util.List;

public interface CityService {

    City create(CityRequestDto request);
    City getById(int id) throws ResourceNotFoundException;
    List<City> getAll();
    City update(int id, CityRequestDto request) throws ResourceNotFoundException;
    void delete(int id) throws ResourceNotFoundException;
}
