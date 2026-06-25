package com.scaler.dbmshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class  Ticket extends BaseModel{

    private double totalAmount;
    @ManyToOne
    private Show show;
    private Long userId;
    @ManyToMany
    private List<Seat> seats;
    @Enumerated
    private TicketStatus ticketStatus;
}
