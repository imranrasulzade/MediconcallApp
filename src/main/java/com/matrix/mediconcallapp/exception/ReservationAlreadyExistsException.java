package com.matrix.mediconcallapp.exception;

public class ReservationAlreadyExistsException extends ConflictException {
    public ReservationAlreadyExistsException(){
        super("Reservation already exists ");
    }
}
