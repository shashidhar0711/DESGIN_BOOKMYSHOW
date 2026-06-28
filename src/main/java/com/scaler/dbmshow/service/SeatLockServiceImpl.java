package com.scaler.dbmshow.service;

import com.scaler.dbmshow.exceptions.SeatAlreadyBookedException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeatLockServiceImpl implements SeatLockService{

    private RedisTemplate<String, Object> redisTemplate;
    private static final long LOCK_TIME = 15;

    public SeatLockServiceImpl( RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    private String getKey(int showId, int seatId) {
        return "seatlock:show:" + showId + ":seat:" + seatId;
    }

    @Override
    public void lockSeat(int showId, List<Integer> seatIds, int userId) throws SeatAlreadyBookedException {


        List<Integer> lockedSeats = new ArrayList<>();

        for(Integer seatId: seatIds) {
            String key = getKey(showId, seatId);
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("userId", userId);
            valueMap.put("lockedAt", System.currentTimeMillis());

            Boolean success = this.redisTemplate.opsForValue()
                    .setIfAbsent(key, valueMap, Duration.ofMinutes(LOCK_TIME));



            if(!Boolean.TRUE.equals(success)) {
                unlockSeats(showId, lockedSeats);

                throw new SeatAlreadyBookedException(
                        "Seat " + seatId + " is already locked."
                );
            }

            lockedSeats.add(seatId);

        }

    }

    @Override
    public boolean unlockSeats(int showId, List<Integer> seatIds) {

        for(Integer seatId: seatIds) {
            this.redisTemplate.delete(getKey(showId, seatId));
        }

        return true;
    }

    @Override
    public boolean isSeatLocked(int showId, int seatId) {
        return Boolean.TRUE.equals(this.redisTemplate.hasKey(getKey(showId, seatId)));

    }
}
