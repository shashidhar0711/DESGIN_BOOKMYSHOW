package com.scaler.dbmshow.controller;

import com.scaler.dbmshow.dtos.TheatreRequestDto;
import com.scaler.dbmshow.dtos.TheatreResponseDto;
import com.scaler.dbmshow.models.Theatre;
import com.scaler.dbmshow.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/theatres")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public TheatreResponseDto createTheatre(
            @RequestBody TheatreRequestDto theatreRequestDto) {

        try {
            Theatre theatre =
                    theatreService.create(theatreRequestDto);
            return TheatreResponseDto.from(theatre);

        } catch (Exception e) {
            return TheatreResponseDto.failure(
                    e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TheatreResponseDto getTheatreById(
            @PathVariable int id) {

        try {
            Theatre theatre =
                    theatreService.getById(id);
            return TheatreResponseDto.from(theatre);

        } catch (Exception e) {
            return TheatreResponseDto.failure(
                    e.getMessage());
        }
    }

    @GetMapping
    public List<TheatreResponseDto> getAllTheatre() {

        List<Theatre> theatres =
                theatreService.getAll();
        List<TheatreResponseDto> response =
                new ArrayList<>();
        for (Theatre theatre : theatres) {
            response.add(
                    TheatreResponseDto.from(theatre));
        }
        return response;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TheatreResponseDto updateTheatre(
            @PathVariable int id,
            @RequestBody TheatreRequestDto theatreRequestDto) {

        try {
            Theatre theatre =
                    theatreService.update(
                            id,
                            theatreRequestDto);
            return TheatreResponseDto.from(theatre);

        } catch (Exception e) {
            return TheatreResponseDto.failure(
                    e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTheatre(
            @PathVariable int id) {

        try {
            theatreService.delete(id);
            return "Theatre deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}