package com.scaler.dbmshow.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Seat extends BaseModel {
    private String name;
    @Enumerated
    private SeatType seatType;
    @ManyToOne
    private Screen screen;
}
