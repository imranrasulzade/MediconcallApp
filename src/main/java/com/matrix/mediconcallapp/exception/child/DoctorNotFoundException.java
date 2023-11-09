package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.NotFoundException;

public class DoctorNotFoundException extends NotFoundException {
    public DoctorNotFoundException() {
        super("Doctor");
    }
}
