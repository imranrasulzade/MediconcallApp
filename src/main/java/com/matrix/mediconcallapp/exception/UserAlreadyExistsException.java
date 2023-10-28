package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.ConflictException;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(){
        super("User already exists ");
    }
}
