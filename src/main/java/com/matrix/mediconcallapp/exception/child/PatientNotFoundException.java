package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.NotFoundException;

public class PatientNotFoundException extends NotFoundException {
    public PatientNotFoundException() {
        super("Patient");
    }
}