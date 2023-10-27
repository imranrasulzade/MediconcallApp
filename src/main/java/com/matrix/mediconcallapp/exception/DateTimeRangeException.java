package com.matrix.mediconcallapp.exception;

public class DateTimeRangeException extends ConflictException{
    public DateTimeRangeException(){
        super("invalid DateTime range");
    }
}
