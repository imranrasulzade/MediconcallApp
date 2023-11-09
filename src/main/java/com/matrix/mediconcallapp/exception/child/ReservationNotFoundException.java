package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.NotFoundException;

public class ReservationNotFoundException extends NotFoundException {
    public ReservationNotFoundException(){
        super("Reservation");
    }
}
