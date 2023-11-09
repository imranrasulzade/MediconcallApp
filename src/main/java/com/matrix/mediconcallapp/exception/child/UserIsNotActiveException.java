package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.IsNotActiveException;

public class UserIsNotActiveException extends IsNotActiveException {
    public UserIsNotActiveException(String ex){
        super("User");
    }
}
