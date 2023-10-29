package com.matrix.mediconcallapp.exception;

import com.matrix.mediconcallapp.exception.parent.ConflictException;

public class FileIOException extends ConflictException {
    public FileIOException(){
        super("Something went wrong with the file directory");
    }
}
