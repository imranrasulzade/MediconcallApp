package com.matrix.mediconcallapp.exception;

public class UserNotFoundException extends NotFoundException{

    public UserNotFoundException(){
        super("User");
    }
}
