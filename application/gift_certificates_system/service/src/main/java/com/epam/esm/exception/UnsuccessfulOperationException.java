package com.epam.esm.exception;

public class UnsuccessfulOperationException extends RuntimeException {

    public UnsuccessfulOperationException() {
    }

    public UnsuccessfulOperationException(String message) {
        super(message);
    }

    public UnsuccessfulOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsuccessfulOperationException(Throwable cause) {
        super(cause);
    }
}
