package com.matrix.mediconcallapp.exception.parent;

public class ConflictException extends RuntimeException{
    public ConflictException(String ex){
        super(ex + " - conflict!");
    }
}
