package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.MovieRequestDto;
import com.scaler.dbmshow.dtos.MovieResponseDto;
import com.scaler.dbmshow.models.Movie;
import com.scaler.dbmshow.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie getMovieById(int id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movies not Found"));
    }

    @Override
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @Override
    public Movie creatMovie(MovieRequestDto request) {
        // create movie object
        // save to db, return it
        Movie movie = new Movie();
        movie.setName(request.getName());
        movie.setGenre(request.getGenre());
        movie.setDirectors(request.getDirectors());
        movie.setActors(request.getActors());

        return this.movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(int id) {
        // get the movie by calling getByMovieById() method
        // delete it and return it
        Movie byMovieById = getMovieById(id);
        this.movieRepository.delete(byMovieById);
    }

    @Override
    public Movie updateMovie(int id, MovieRequestDto request) {
        // get the movie by calling getByMovieById() method
        // update with same and save it to db
        // return it
        Movie byMovieById = getMovieById(id);
        byMovieById.setName(request.getName());
        byMovieById.setGenre(request.getGenre());
        byMovieById.setDirectors(request.getDirectors());
        byMovieById.setActors(request.getActors());

        return this.movieRepository.save(byMovieById);
    }
}
