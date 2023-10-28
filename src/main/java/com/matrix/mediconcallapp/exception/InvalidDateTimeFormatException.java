package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.BadRequestException;

public class InvalidDateTimeFormatException extends BadRequestException {
    public InvalidDateTimeFormatException(){
        super("Invalid DateTime format");
    }
}
