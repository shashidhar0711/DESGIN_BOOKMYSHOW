package com.scaler.dbmshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Seat extends BaseModel {
    private String name;
//    @Enumerated
    private SeatType seatType;
}
