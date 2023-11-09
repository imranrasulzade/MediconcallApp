package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.ConflictException;

public class ReservationAlreadyExistsException extends ConflictException {
    public ReservationAlreadyExistsException(){
        super("Reservation already exists ");
    }
}
