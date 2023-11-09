package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.BadRequestException;

public class DateTimeRangeException extends BadRequestException {
    public DateTimeRangeException(){
        super("invalid DateTime range");
    }
}
