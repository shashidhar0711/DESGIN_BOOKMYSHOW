package com.scaler.dbmshow.controller;

import com.scaler.dbmshow.dtos.ScreenRequestDto;
import com.scaler.dbmshow.dtos.ScreenResponseDto;
import com.scaler.dbmshow.models.Screen;
import com.scaler.dbmshow.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/screens")
public class ScreenController {
    @Autowired
    private ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ScreenResponseDto createScreen(@RequestBody ScreenRequestDto screenRequestDto) {
        try {
            Screen screen = screenService.create(screenRequestDto);
            return ScreenResponseDto.from(screen);
        } catch (Exception e) {
            return ScreenResponseDto.failure(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ScreenResponseDto getScreenById(@PathVariable int id) {
        try {
            Screen screen = screenService.getById(id);
            return ScreenResponseDto.from(screen);
        } catch (Exception e) {
            return ScreenResponseDto.failure(e.getMessage());
        }
    }

    @GetMapping
    public List<ScreenResponseDto> getAllScreens() {
        List<Screen> screens = screenService.getAll();

        List<ScreenResponseDto> response = new ArrayList<>();

        for (Screen screen : screens) {
            response.add(ScreenResponseDto.from(screen));
        }

        return response;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ScreenResponseDto updateScreen(@PathVariable int id, @RequestBody ScreenRequestDto screenRequestDto) {
        try {
            Screen screen = screenService.update(id, screenRequestDto);
            return ScreenResponseDto.from(screen);
        } catch (Exception e) {
            return ScreenResponseDto.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteScreen(@PathVariable int id) {
        try {
            screenService.delete(id);
            return "Screen deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
