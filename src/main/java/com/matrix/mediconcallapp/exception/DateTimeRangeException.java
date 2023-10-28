package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.BadRequestException;

public class DateTimeRangeException extends BadRequestException {
    public DateTimeRangeException(){
        super("invalid DateTime range");
    }
}
