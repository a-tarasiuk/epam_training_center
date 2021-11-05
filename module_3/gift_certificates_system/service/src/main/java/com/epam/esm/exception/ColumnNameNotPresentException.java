package com.epam.esm.exception;

public class ColumnNameNotPresentException extends RuntimeException {

    public ColumnNameNotPresentException() {
    }

    public ColumnNameNotPresentException(String message) {
        super(message);
    }

    public ColumnNameNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ColumnNameNotPresentException(Throwable cause) {
        super(cause);
    }
}
