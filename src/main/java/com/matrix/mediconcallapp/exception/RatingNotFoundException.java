package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.NotFoundException;

public class RatingNotFoundException extends NotFoundException {
    public RatingNotFoundException() {
        super("Rating ");
    }
}
