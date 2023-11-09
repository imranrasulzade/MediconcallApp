package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.BadRequestException;

public class InvalidDateTimeFormatException extends BadRequestException {
    public InvalidDateTimeFormatException(){
        super("Invalid DateTime format");
    }
}
