package com.scaler.dbmshow.repositories;

import com.scaler.dbmshow.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Integer> {

    List<ShowSeatType> findAllByShow(int showId);
}
