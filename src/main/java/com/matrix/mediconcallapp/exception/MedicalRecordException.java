package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.NotFoundException;

public class MedicalRecordException extends NotFoundException {
    public MedicalRecordException(){
        super("Record not found");
    }
}
