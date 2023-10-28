package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.ConflictException;

public class ReservationAlreadyExistsException extends ConflictException {
    public ReservationAlreadyExistsException(){
        super("Reservation already exists ");
    }
}
