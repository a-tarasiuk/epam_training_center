package com.epam.esm.exception;

public class EntityInvalidException extends RuntimeException {

    public EntityInvalidException() {
    }

    public EntityInvalidException(String message) {
        super(message);
    }

    public EntityInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityInvalidException(Throwable cause) {
        super(cause);
    }
}
