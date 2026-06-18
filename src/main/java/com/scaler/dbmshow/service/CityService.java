package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.CityRequestDto;
import com.scaler.dbmshow.models.City;

import java.util.List;

public interface CityService {

    City create(CityRequestDto request);
    City getById(int id);
    List<City> getAll();
    City update(int id, CityRequestDto request);
    void delete(int id);
}
