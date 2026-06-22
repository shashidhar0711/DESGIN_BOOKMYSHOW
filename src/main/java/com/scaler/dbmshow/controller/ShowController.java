package com.scaler.dbmshow.controller;

import com.scaler.dbmshow.dtos.ResponseType;
import com.scaler.dbmshow.dtos.ShowRequestDto;
import com.scaler.dbmshow.dtos.ShowResponseDto;
import com.scaler.dbmshow.models.Show;
import com.scaler.dbmshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ShowResponseDto createShow(@RequestBody ShowRequestDto showRequestDto) {
        ShowResponseDto showResponseDto = new ShowResponseDto();
        try {
           Show show  = this.showService.createShow(showRequestDto);
           showResponseDto.setShow(show);
           showResponseDto.setResponseType(ResponseType.SUCCESS);
        } catch(Exception e) {
            showResponseDto.setErrorMessage(e.getMessage());
        }
        return showResponseDto;
    }

}
