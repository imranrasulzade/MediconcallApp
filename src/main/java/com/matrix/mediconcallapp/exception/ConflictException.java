package com.matrix.mediconcallapp.exception;

public class ConflictException extends RuntimeException{
    public ConflictException(String ex){
        super(ex + " - conflict!");
    }
}
