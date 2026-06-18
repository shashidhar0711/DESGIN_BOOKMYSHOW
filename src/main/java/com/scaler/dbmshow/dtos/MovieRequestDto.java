package com.scaler.dbmshow.dtos;

import com.scaler.dbmshow.models.Genre;
import lombok.Data;

import java.util.List;

@Data
public class MovieRequestDto {
    private String name;
    private Genre genre;
    private List<String> directors;
    private List<String>  actors;
}
