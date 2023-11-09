package com.matrix.mediconcallapp.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String ex){
        super(ex);
    }
}
