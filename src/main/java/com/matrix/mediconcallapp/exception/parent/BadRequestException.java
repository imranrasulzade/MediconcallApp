package com.matrix.mediconcallapp.exception.parent;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String ex){
        super(ex);
    }
}
