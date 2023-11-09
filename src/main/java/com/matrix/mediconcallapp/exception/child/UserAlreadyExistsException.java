package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.ConflictException;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(){
        super("User already exists ");
    }
}
