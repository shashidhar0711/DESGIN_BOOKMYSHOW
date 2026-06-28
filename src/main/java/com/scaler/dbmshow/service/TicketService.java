package com.scaler.dbmshow.service;

import com.scaler.dbmshow.dtos.BookTicketResultDto;
import com.scaler.dbmshow.exceptions.*;
import com.scaler.dbmshow.models.Ticket;
import com.scaler.dbmshow.repositories.TicketRepository;

import java.util.List;

public interface TicketService {

    public BookTicketResultDto bookTicket(List<Integer> seatIds, int showId, Long userId) throws InvalidRequestException, UnAvailableSeatsException, SeatAlreadyBookedException, ResourceNotFoundException, ShowCannotBeBookedException, UnableToCreatePaymentLinkException;

    public Ticket getTicketDetails(int ticketId) throws ResourceNotFoundException;

    public void confirmBooking(int ticketId) throws UnAvailableSeatsException;

}
