package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.IsNotActiveException;

public class UserIsNotActiveException extends IsNotActiveException {
    public UserIsNotActiveException(String ex){
        super("User");
    }
}
