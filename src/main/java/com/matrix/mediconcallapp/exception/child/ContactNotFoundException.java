package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.NotFoundException;

public class ContactNotFoundException extends NotFoundException {
    public ContactNotFoundException(){
        super("Contact");
    }
}
