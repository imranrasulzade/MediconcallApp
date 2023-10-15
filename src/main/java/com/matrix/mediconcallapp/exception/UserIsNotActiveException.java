package com.matrix.mediconcallapp.exception;

public class UserIsNotActiveException extends RuntimeException {
    public UserIsNotActiveException(String ex){
        super("User is not active");
    }
}
