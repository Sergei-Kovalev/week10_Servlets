package com.gmail.kovalev.exception;

public class SaverNotFoundException extends RuntimeException {
    public SaverNotFoundException(String message) {
        super("This application cannot save information in files with the extension = " + message);
    }
}
