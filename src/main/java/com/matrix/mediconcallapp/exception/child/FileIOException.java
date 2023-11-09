package com.matrix.mediconcallapp.exception.child;

import com.matrix.mediconcallapp.exception.ConflictException;

public class FileIOException extends ConflictException {
    public FileIOException(){
        super("Something went wrong with the file directory");
    }
}
