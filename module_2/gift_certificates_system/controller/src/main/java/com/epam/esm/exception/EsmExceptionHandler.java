package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EsmExceptionHandler {
    private static final int HTTP_ERROR_CODE_ENTITY_EXISTS = 40601;
    private static final int HTTP_ERROR_CODE_ENTITY_NOT_FOUND = 40602;

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<EsmTemplateException> handleEntityExistsException(RuntimeException e) {
        HttpStatus httpStatus = HttpStatus.FOUND;

        EsmExceptionBody body = new EsmExceptionBody(e.getMessage(), HTTP_ERROR_CODE_ENTITY_EXISTS);
        EsmTemplateException response = new EsmTemplateException(httpStatus, body);

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<EsmTemplateException> handleEntityNotFoundException(RuntimeException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        EsmExceptionBody body = new EsmExceptionBody(e.getMessage(), HTTP_ERROR_CODE_ENTITY_NOT_FOUND);
        EsmTemplateException response = new EsmTemplateException(httpStatus, body);

        return new ResponseEntity<>(response, httpStatus);
    }
}
