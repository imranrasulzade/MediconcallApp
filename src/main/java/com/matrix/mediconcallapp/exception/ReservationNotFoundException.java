package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.NotFoundException;

public class ReservationNotFoundException extends NotFoundException {
    public ReservationNotFoundException(){
        super("Reservation");
    }
}
