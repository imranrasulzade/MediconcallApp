package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.NotFoundException;

public class ContactNotFoundException extends NotFoundException {
    public ContactNotFoundException(){
        super("Contact");
    }
}
