package com.scaler.dbmshow.repositories;

import com.scaler.dbmshow.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
