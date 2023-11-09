package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.BadRequestException;

public class PasswordWrongException extends BadRequestException {
    public PasswordWrongException() {
        super("Old password entered incorrectly or new passwords do not match");
    }
}
