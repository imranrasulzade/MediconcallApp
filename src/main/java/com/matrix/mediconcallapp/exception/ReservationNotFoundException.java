package com.matrix.mediconcallapp.exception;

public class ReservationNotFoundException extends NotFoundException{
    public ReservationNotFoundException(){
        super("Reservation");
    }
}
