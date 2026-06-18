package com.scaler.dbmshow.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.dbmshow.models.Genre;
import com.scaler.dbmshow.models.Movie;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MovieResponseDto {
    private int id;
    private String name;
    private Genre genre;
    private List<String> directors;
    private List<String> actors;
    private String errorMessage;
    private ResponseType responseType;

    public static MovieResponseDto from (Movie movie) {
        MovieResponseDto dto = new MovieResponseDto();

        dto.setId(movie.getId());
        dto.setName(movie.getName());
        dto.setGenre(movie.getGenre());
        dto.setDirectors(movie.getDirectors());
        dto.setActors(movie.getActors());
        dto.setResponseType(ResponseType.SUCCESS);

        return dto;
    }

    public static MovieResponseDto failure(String errorMessage) {
        MovieResponseDto dto = new MovieResponseDto();

        dto.setResponseType(ResponseType.FAILURE);
        dto.setErrorMessage(errorMessage);

        return dto;
    }
}
