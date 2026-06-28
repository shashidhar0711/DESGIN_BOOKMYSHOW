package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.CityRequestDto;
import com.scaler.dbmshow.exceptions.ResourceNotFoundException;
import com.scaler.dbmshow.models.City;
import com.scaler.dbmshow.repositories.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    void testCreateCity() {

        CityRequestDto request = new CityRequestDto();
        request.setName("Chennai");

        City savedCity = new City();
        savedCity.setId(1);
        savedCity.setName("Chennai");

        when(cityRepository.save(any(City.class))).thenReturn(savedCity);

        City result = cityService.create(request);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Chennai", result.getName());

        verify(cityRepository).save(any(City.class));
    }

    @Test
    void testGetCityById() throws ResourceNotFoundException {

        City city = new City();
        city.setId(1);
        city.setName("Chennai");

        when(cityRepository.findById(1))
                .thenReturn(Optional.of(city));

        City result = cityService.getById(1);

        assertEquals(1, result.getId());
        assertEquals("Chennai", result.getName());

        verify(cityRepository).findById(1);
    }

    @Test
    void testGetCityById_NotFound() {

        when(cityRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> cityService.getById(1));

        verify(cityRepository).findById(1);
    }

    @Test
    void testGetAllCities() {

        City city1 = new City();
        city1.setId(1);
        city1.setName("Chennai");

        City city2 = new City();
        city2.setId(2);
        city2.setName("Bangalore");

        when(cityRepository.findAll())
                .thenReturn(List.of(city1, city2));

        List<City> cities = cityService.getAll();

        assertEquals(2, cities.size());
        assertEquals("Chennai", cities.get(0).getName());
        assertEquals("Bangalore", cities.get(1).getName());

        verify(cityRepository).findAll();
    }

    @Test
    void testUpdateCity() throws ResourceNotFoundException {

        City existing = new City();
        existing.setId(1);
        existing.setName("Old Name");

        City updated = new City();
        updated.setId(1);
        updated.setName("Hyderabad");

        CityRequestDto request = new CityRequestDto();
        request.setName("Hyderabad");

        when(cityRepository.findById(1))
                .thenReturn(Optional.of(existing));

        when(cityRepository.save(any(City.class)))
                .thenReturn(updated);

        City result = cityService.update(1, request);

        assertEquals("Hyderabad", result.getName());

        verify(cityRepository).findById(1);
        verify(cityRepository).save(any(City.class));
    }

    @Test
    void testUpdateCity_NotFound() {

        CityRequestDto request = new CityRequestDto();
        request.setName("Hyderabad");

        when(cityRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> cityService.update(1, request));

        verify(cityRepository).findById(1);
        verify(cityRepository, never()).save(any());
    }

    @Test
    void testDeleteCity() throws ResourceNotFoundException {

        City city = new City();
        city.setId(1);
        city.setName("Chennai");

        when(cityRepository.findById(1))
                .thenReturn(Optional.of(city));

        doNothing().when(cityRepository).delete(city);

        cityService.delete(1);

        verify(cityRepository).findById(1);
        verify(cityRepository).delete(city);
    }

    @Test
    void testDeleteCity_NotFound() {

        when(cityRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> cityService.delete(1));

        verify(cityRepository).findById(1);
        verify(cityRepository, never()).delete(any());
    }
}