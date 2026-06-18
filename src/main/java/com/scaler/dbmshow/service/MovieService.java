package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.MovieRequestDto;
import com.scaler.dbmshow.dtos.MovieResponseDto;
import com.scaler.dbmshow.models.Movie;

import java.util.List;

public interface MovieService {

    Movie getMovieById(int id);

    List<Movie> getAllMovies();

    Movie creatMovie(MovieRequestDto movieRequestDto);

    void deleteMovie(int id);

    Movie updateMovie(int id, MovieRequestDto movieRequestDto);
}
