package com.scaler.dbmshow.controller;

import com.scaler.dbmshow.dtos.ResponseType;
import com.scaler.dbmshow.dtos.ShowRequestDto;
import com.scaler.dbmshow.dtos.ShowResponseDto;
import com.scaler.dbmshow.models.Show;
import com.scaler.dbmshow.security.JwtUserDto;
import com.scaler.dbmshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ShowResponseDto createShow(@RequestBody ShowRequestDto showRequestDto,
                                      Authentication authentication) {
        ShowResponseDto showResponseDto = new ShowResponseDto();
        try {
            JwtUserDto user = (JwtUserDto) authentication.getPrincipal();
           Show show  = this.showService.createShow(showRequestDto, user.getUserId());
           showResponseDto.setShow(show);
           showResponseDto.setResponseType(ResponseType.SUCCESS);
        } catch(Exception e) {
            showResponseDto.setErrorMessage(e.getMessage());
        }
        return showResponseDto;
    }
}
