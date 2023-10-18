package com.matrix.mediconcallapp.exception;

public class UserAlreadyExistsException extends ConflictException{
    public UserAlreadyExistsException(){
        super("User already exists ");
    }
}
