package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.NotFoundException;

public class RatingNotFoundException extends NotFoundException {
    public RatingNotFoundException() {
        super("Rating ");
    }
}
