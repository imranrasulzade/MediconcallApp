package com.matrix.mediconcallapp.exception;

public class DoctorNotFoundException extends NotFoundException{
    public DoctorNotFoundException() {
        super("Doctor");
    }
}
