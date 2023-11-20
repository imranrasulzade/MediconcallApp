package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.ConflictException;

public class ContactAlreadyExistsException extends ConflictException {
    public ContactAlreadyExistsException(){
        super("Contact already exists!");
    }
}
