package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.ShowRequestDto;
import com.scaler.dbmshow.models.Show;

public interface ShowService {
    Show createShow(ShowRequestDto showRequestDto);
}
