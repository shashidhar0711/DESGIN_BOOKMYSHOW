package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.MovieRequestDto;
import com.scaler.dbmshow.dtos.MovieResponseDto;
import com.scaler.dbmshow.exceptions.ResourceNotFoundException;
import com.scaler.dbmshow.models.Movie;
import com.scaler.dbmshow.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;
    private RedisTemplate redisTemplate;

    public MovieServiceImpl(MovieRepository movieRepository,
                            RedisTemplate redisTemplate) {
        this.movieRepository = movieRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Movie getMovieById(int id) throws ResourceNotFoundException {
        String key = "movie:" + id;

        try {
            Movie movie = (Movie) redisTemplate.opsForValue().get(key);

            if (movie != null) {
                System.out.println("Movie fetched from Redis");
                return movie;
            }
        } catch (Exception e) {
            System.out.println("Redis unavailable. Fetching from DB.");
        }

        Movie movie = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        try {
            redisTemplate.opsForValue().set(key, movie);
            System.out.println("Movie cached in Redis");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to cache movie");
        }

        return movie;
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
    public void deleteMovie(int id) throws ResourceNotFoundException {
        // get the movie by calling getByMovieById() method
        // delete it and return it
        Movie byMovieById = getMovieById(id);
        this.movieRepository.delete(byMovieById);
    }

    @Override
    public Movie updateMovie(int id, MovieRequestDto request) throws ResourceNotFoundException {
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
