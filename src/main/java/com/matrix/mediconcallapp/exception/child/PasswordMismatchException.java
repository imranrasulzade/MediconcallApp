package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.BadRequestException;

public class PasswordMismatchException extends BadRequestException {
    public PasswordMismatchException(){
        super("Retry password do not matching new password");
    }
}
