package com.scaler.dbmshow.controller;

import com.scaler.dbmshow.dtos.CityRequestDto;
import com.scaler.dbmshow.dtos.CityResponseDto;
import com.scaler.dbmshow.models.City;
import com.scaler.dbmshow.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CityResponseDto createCity(
            @RequestBody CityRequestDto request) {

        try {
            City city = cityService.create(request);
            return CityResponseDto.from(city);
        } catch (Exception e) {
            return CityResponseDto.failure(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CityResponseDto getCityById(
            @PathVariable int id) {

        try {
            City city = cityService.getById(id);
            return CityResponseDto.from(city);
        } catch (Exception e) {
            return CityResponseDto.failure(e.getMessage());
        }
    }

    @GetMapping
    public List<CityResponseDto> getAllCities() {

        List<City> cities = cityService.getAll();
        List<CityResponseDto> response = new ArrayList<>();

        for (City city : cities) {
            response.add(CityResponseDto.from(city));
        }
        return response;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CityResponseDto updateCity(
            @PathVariable int id,
            @RequestBody CityRequestDto request) {

        try {
            City city = cityService.update(id, request);
            return CityResponseDto.from(city);
        } catch (Exception e) {
            return CityResponseDto.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCity(
            @PathVariable int id) {

        try {
            cityService.delete(id);
            return "City deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
