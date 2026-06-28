package com.scaler.dbmshow.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.dbmshow.dtos.CityRequestDto;
import com.scaler.dbmshow.models.City;
import com.scaler.dbmshow.security.JwtService;
import com.scaler.dbmshow.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CityController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CityService cityService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateCity() throws Exception {

        CityRequestDto request = new CityRequestDto();
        request.setName("Chennai");

        City city = new City();
        city.setId(1);
        city.setName("Chennai");

        when(cityService.create(any(CityRequestDto.class))).thenReturn(city);

        mockMvc.perform(post("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Chennai"))
                .andExpect(jsonPath("$.responseType").value("SUCCESS"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetCityById() throws Exception {

        City city = new City();
        city.setId(1);
        city.setName("Chennai");

        when(cityService.getById(1)).thenReturn(city);

        mockMvc.perform(get("/cities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Chennai"))
                .andExpect(jsonPath("$.responseType").value("SUCCESS"));
    }

    @Test
    void testGetAllCities() throws Exception {

        City city1 = new City();
        city1.setId(1);
        city1.setName("Chennai");

        City city2 = new City();
        city2.setId(2);
        city2.setName("Bangalore");

        when(cityService.getAll()).thenReturn(List.of(city1, city2));

        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Chennai"))
                .andExpect(jsonPath("$[0].responseType").value("SUCCESS"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Bangalore"))
                .andExpect(jsonPath("$[1].responseType").value("SUCCESS"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateCity() throws Exception {

        CityRequestDto request = new CityRequestDto();
        request.setName("Hyderabad");

        City city = new City();
        city.setId(1);
        city.setName("Hyderabad");

        when(cityService.update(eq(1), any(CityRequestDto.class))).thenReturn(city);

        mockMvc.perform(put("/cities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hyderabad"))
                .andExpect(jsonPath("$.responseType").value("SUCCESS"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteCity() throws Exception {

        doNothing().when(cityService).delete(1);

        mockMvc.perform(delete("/cities/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("City deleted successfully"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateCityFailure() throws Exception {

        CityRequestDto request = new CityRequestDto();
        request.setName("Chennai");

        when(cityService.create(any(CityRequestDto.class)))
                .thenThrow(new RuntimeException("City already exists"));

        mockMvc.perform(post("/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorMessage").value("City already exists"))
                .andExpect(jsonPath("$.responseType").value("FAILURE"));
    }
}