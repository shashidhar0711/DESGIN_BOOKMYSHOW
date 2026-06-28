package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.CityRequestDto;
import com.scaler.dbmshow.exceptions.ResourceNotFoundException;
import com.scaler.dbmshow.models.City;
import com.scaler.dbmshow.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService{

    @Autowired
    private CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City create(CityRequestDto request) {
        City city = new City();
        city.setName(request.getName());
        return cityRepository.save(city);
    }

    @Override
    public City getById(int id) throws ResourceNotFoundException {
        return cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));
    }

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    @Override
    public City update(int id, CityRequestDto request) throws ResourceNotFoundException {
        City city = getById(id);
        city.setName(request.getName());
        return cityRepository.save(city);
    }

    @Override
    public void delete(int id) throws ResourceNotFoundException {
        City city = getById(id);
        cityRepository.delete(city);
    }
}
