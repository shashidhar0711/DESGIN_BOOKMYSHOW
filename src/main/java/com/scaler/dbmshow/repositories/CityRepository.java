package com.scaler.dbmshow.repositories;

import com.scaler.dbmshow.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
}
