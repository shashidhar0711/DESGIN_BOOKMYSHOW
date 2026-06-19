package com.scaler.dbmshow.service;

import com.scaler.dbmshow.exceptions.InvalidRequestException;
import com.scaler.dbmshow.exceptions.UnAvailableSeatsException;
import com.scaler.dbmshow.models.Ticket;

import java.util.List;

public interface
TicketService {

    public Ticket bookTicket(List<Integer> seatIds, int showId, int userId) throws InvalidRequestException, UnAvailableSeatsException;
}
