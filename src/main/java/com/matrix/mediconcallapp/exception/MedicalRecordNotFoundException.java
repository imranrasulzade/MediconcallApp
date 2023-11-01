package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.NotFoundException;

public class MedicalRecordNotFoundException extends NotFoundException {
    public MedicalRecordNotFoundException(){
        super("Medical Record ");
    }
}
