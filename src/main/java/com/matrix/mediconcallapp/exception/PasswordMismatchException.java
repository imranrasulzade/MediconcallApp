package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.BadRequestException;

public class PasswordMismatchException extends BadRequestException {
    public PasswordMismatchException(){
        super("Retry password do not matching new password");
    }
}
