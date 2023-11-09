package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(){
        super("User");
    }
}
