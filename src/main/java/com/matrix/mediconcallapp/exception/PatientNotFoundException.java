package com.matrix.mediconcallapp.exception;

public class PatientNotFoundException extends NotFoundException{
    public PatientNotFoundException() {
        super("Patient");
    }
}