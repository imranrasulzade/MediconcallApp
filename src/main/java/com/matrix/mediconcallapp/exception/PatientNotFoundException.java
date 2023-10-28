package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.NotFoundException;

public class PatientNotFoundException extends NotFoundException {
    public PatientNotFoundException() {
        super("Patient");
    }
}