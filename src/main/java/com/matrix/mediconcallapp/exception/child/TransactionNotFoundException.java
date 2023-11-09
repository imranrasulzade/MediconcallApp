package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.NotFoundException;

public class TransactionNotFoundException extends NotFoundException {
    public TransactionNotFoundException() {
        super("Transaction ");
    }
}
