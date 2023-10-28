package com.matrix.mediconcallapp.exception.parent;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String ex) {
        super(ex + " Not found!");
    }
}
