package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.BookTicketResultDto;
import com.scaler.dbmshow.exceptions.InvalidRequestException;
import com.scaler.dbmshow.exceptions.UnAvailableSeatsException;
import com.scaler.dbmshow.models.Ticket;
import com.scaler.dbmshow.repositories.TicketRepository;

import java.util.List;

public interface TicketService {

    public BookTicketResultDto bookTicket(List<Integer> seatIds, int showId, Long userId) throws InvalidRequestException, UnAvailableSeatsException;

    public Ticket getTicketDetails(int ticketId);

    public void confirmBooking(int ticketId);

}
