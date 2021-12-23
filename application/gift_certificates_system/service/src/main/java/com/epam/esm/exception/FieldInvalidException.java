package com.epam.esm.exception;

public class FieldInvalidException extends RuntimeException {

    public FieldInvalidException() {
    }

    public FieldInvalidException(String message) {
        super(message);
    }

    public FieldInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldInvalidException(Throwable cause) {
        super(cause);
    }
}
