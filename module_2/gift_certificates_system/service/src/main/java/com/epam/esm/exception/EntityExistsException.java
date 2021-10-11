package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class EntityExistsException extends RuntimeException {

    public EntityExistsException() {
    }

    public EntityExistsException(String message) {
        super(message);
    }

    public EntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityExistsException(Throwable cause) {
        super(cause);
    }
}
