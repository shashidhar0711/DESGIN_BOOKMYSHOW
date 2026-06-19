package com.scaler.dbmshow.repositories;

import com.scaler.dbmshow.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    List<Seat> findAllByScreenId(int screenId);
    List<Seat> findAllByIdIn(List<Integer> seatIds);
}
