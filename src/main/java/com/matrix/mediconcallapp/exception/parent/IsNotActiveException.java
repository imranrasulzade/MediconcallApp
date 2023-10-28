package com.matrix.mediconcallapp.exception.parent;

public class IsNotActiveException extends RuntimeException{
    public IsNotActiveException(String ex){
        super(ex + " is not active");
    }
}
