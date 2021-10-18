package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

import static com.epam.esm.exception.HttpCustomErrorCode.COLUMN_NAME_NOT_PRESENT;
import static com.epam.esm.exception.HttpCustomErrorCode.ENTITY_EXISTS;
import static com.epam.esm.exception.HttpCustomErrorCode.ENTITY_INVALID;
import static com.epam.esm.exception.HttpCustomErrorCode.ENTITY_INVALID_FIELD;
import static com.epam.esm.exception.HttpCustomErrorCode.ENTITY_NOT_FOUND;
import static com.epam.esm.exception.HttpCustomErrorCode.ENUM_CONSTANT_NOT_PRESENT;

@ControllerAdvice
public class EsmExceptionHandler {
    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public EsmExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<EsmTemplateException> handleEntityExistsException(EntityExistsException e, Locale locale) {
        return createResponseEntity(e, locale, ENTITY_EXISTS, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<EsmTemplateException> handleEntityNotFoundException(EntityNotFoundException e, Locale locale) {
        return createResponseEntity(e, locale, ENTITY_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityInvalidException.class)
    public ResponseEntity<EsmTemplateException> handleEntityFieldValidateException(EntityInvalidException e, Locale locale) {
        return createResponseEntity(e, locale, ENTITY_INVALID, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FieldInvalidException.class)
    public ResponseEntity<EsmTemplateException> handleFieldInvalidException(FieldInvalidException e, Locale locale) {
        return createResponseEntity(e, locale, ENTITY_INVALID_FIELD, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EnumConstantNotPresentException.class)
    public ResponseEntity<EsmTemplateException> handleEnumConstantNotPresentException(EnumConstantNotPresentException e, Locale locale) {
        return createResponseEntity(e, locale, ENUM_CONSTANT_NOT_PRESENT, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ColumnNameNotPresentException.class)
    public ResponseEntity<EsmTemplateException> handleColumnNameNotPresentException(ColumnNameNotPresentException e, Locale locale) {
        return createResponseEntity(e, locale, COLUMN_NAME_NOT_PRESENT, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<EsmTemplateException> handleIllegalArgumentException(IllegalArgumentException e, Locale locale) {
        return createResponseEntity(e, locale, ENUM_CONSTANT_NOT_PRESENT, HttpStatus.CONFLICT);
    }

    private ResponseEntity<EsmTemplateException> createResponseEntity(RuntimeException runtimeException, Locale locale, HttpCustomErrorCode httpCustomErrorCode, HttpStatus httpStatus) {
        String exceptionMessage = messageSource.getMessage(runtimeException.getMessage(), null, locale);
        EsmExceptionBody exceptionBody = new EsmExceptionBody(exceptionMessage, httpCustomErrorCode);
        EsmTemplateException templateException = new EsmTemplateException(httpStatus, exceptionBody);
        return new ResponseEntity<>(templateException, httpStatus);
    }
}