package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.ConflictException;

public class RatingAlreadyExistsException extends ConflictException {

    public RatingAlreadyExistsException() {
        super("Rating already exists!");
    }
}
