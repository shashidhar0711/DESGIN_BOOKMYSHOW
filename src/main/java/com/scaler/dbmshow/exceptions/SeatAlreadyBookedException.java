package com.scaler.dbmshow.exceptions;

public class SeatAlreadyBookedException extends  Exception{
    public SeatAlreadyBookedException(String message) {
        super(message);
    }
}
