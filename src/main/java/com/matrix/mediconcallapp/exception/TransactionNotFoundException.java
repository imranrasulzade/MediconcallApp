package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.NotFoundException;

public class TransactionNotFoundException extends NotFoundException {
    public TransactionNotFoundException() {
        super("Transaction ");
    }
}
