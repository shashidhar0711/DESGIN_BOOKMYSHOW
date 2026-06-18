package com.scaler.dbmshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ShowSeatType extends BaseModel{
    private SeatType seatType;
    @ManyToOne
    private Show show;
    private double amount;
}
