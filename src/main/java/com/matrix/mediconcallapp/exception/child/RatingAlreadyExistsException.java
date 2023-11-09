package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.ConflictException;

public class RatingAlreadyExistsException extends ConflictException {

    public RatingAlreadyExistsException() {
        super("Rating already exists!");
    }
}
