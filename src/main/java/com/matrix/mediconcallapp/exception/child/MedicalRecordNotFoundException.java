package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.NotFoundException;

public class MedicalRecordNotFoundException extends NotFoundException {
    public MedicalRecordNotFoundException(){
        super("Medical Record ");
    }
}
