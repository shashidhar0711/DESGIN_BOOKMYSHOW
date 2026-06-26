package com.scaler.dbmshow.repositories;

import com.scaler.dbmshow.models.SeatStatus;
import com.scaler.dbmshow.models.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Integer> {

    List<ShowSeat> findAllByIdIn(List<Integer> seatIds);

    List<ShowSeat> findAllByShow_IdAndSeatStatus(int showId, SeatStatus seatStatus);

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<ShowSeat> findAllByShow_IdAndSeat_IdInAndSeatStatus(
            int showId,
            List<Integer> seatIds,
            SeatStatus seatStatus
    );

}
