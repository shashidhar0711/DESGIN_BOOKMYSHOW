package com.scaler.dbmshow.controller;

import com.scaler.dbmshow.dtos.MovieRequestDto;
import com.scaler.dbmshow.dtos.MovieResponseDto;
import com.scaler.dbmshow.models.Movie;
import com.scaler.dbmshow.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieContoller {

    @Autowired
    private MovieService movieService;

    public MovieContoller(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public MovieResponseDto createMovie(@RequestBody MovieRequestDto movieRequestDto) {
        try {
            Movie movie = this.movieService.creatMovie(movieRequestDto);
            return MovieResponseDto.from(movie);
        } catch (Exception e) {
            return MovieResponseDto.failure(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public MovieResponseDto getMovieById(@PathVariable int id) {
        try {
            Movie movie = movieService.getMovieById(id);
            return MovieResponseDto.from(movie);
        } catch (Exception e) {
            return MovieResponseDto.failure(e.getMessage());
        }
    }

    @GetMapping
    public List<MovieResponseDto> getAllMovies() {
        List<Movie> allMovies = this.movieService.getAllMovies();
        List<MovieResponseDto> responseList = new ArrayList<>();

        for(Movie movie: allMovies) {
            responseList.add(MovieResponseDto.from(movie));
        }

        return responseList;
    }

    @PutMapping("/{id}")
    public MovieResponseDto updateMovie(@PathVariable int id, @RequestBody MovieRequestDto movieRequestDto) {
        try {
            Movie movie = movieService.updateMovie(id, movieRequestDto);
            return MovieResponseDto.from(movie);

        } catch (Exception e) {
            return MovieResponseDto.failure(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable int id) {
        try {
            movieService.deleteMovie(id);
            return "Movie deleted successfully";

        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
