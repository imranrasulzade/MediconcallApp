package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(){
        super("User");
    }
}
