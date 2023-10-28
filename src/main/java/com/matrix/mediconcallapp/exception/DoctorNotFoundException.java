package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.NotFoundException;

public class DoctorNotFoundException extends NotFoundException {
    public DoctorNotFoundException() {
        super("Doctor");
    }
}
