package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.TheatreRequestDto;
import com.scaler.dbmshow.exceptions.ResourceNotFoundException;
import com.scaler.dbmshow.models.City;
import com.scaler.dbmshow.models.Theatre;
import com.scaler.dbmshow.repositories.CityRepository;
import com.scaler.dbmshow.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreServiceImpl implements TheatreService{

    @Autowired
    private TheatreRepository theatreRepository;
    private CityRepository cityRepository;

    public TheatreServiceImpl(TheatreRepository theatreRepository, CityRepository cityRepository) {
        this.theatreRepository = theatreRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public Theatre create(TheatreRequestDto request) throws ResourceNotFoundException {

        City city = this.cityRepository.findById(request.getCityId())
                            .orElseThrow(() -> new ResourceNotFoundException("City not found!"));

        Theatre theatre = new Theatre();
        theatre.setName(request.getName());
        theatre.setAddress(request.getAddress());
        theatre.setCity(city);

        return theatreRepository.save(theatre);
    }

    @Override
    public Theatre getById(int id) {
        return theatreRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Theatre not found"));
    }

    @Override
    public List<Theatre> getAll() {
        return this.theatreRepository.findAll();
    }

    @Override
    public Theatre update(int id, TheatreRequestDto request) throws ResourceNotFoundException {
        City city = this.cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City not found!"));

        Theatre theatre = new Theatre();
        theatre.setName(request.getName());
        theatre.setAddress(request.getAddress());
        theatre.setCity(city);

        return theatreRepository.save(theatre);
    }

    @Override
    public void delete(int id) {
        Theatre byId = getById(id);

        theatreRepository.delete(byId);
    }
}
