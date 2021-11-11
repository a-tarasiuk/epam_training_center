package com.epam.esm.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class EsmExceptionHandler {
    private final ResourceBundleMessageSource messageSource;

    public EsmExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e, Locale locale) {
        EsmHttpErrorCode esmHttpErrorCode = EsmHttpErrorCode.INVALID_ARGUMENT_METHOD;
        return createResponseEntity(e, HttpStatus.CONFLICT, esmHttpErrorCode, locale);
    }

    @ExceptionHandler(ConversionFailedException.class)
    private ResponseEntity<?> handleEntityNotFoundException(RuntimeException e, Locale locale) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e, Locale locale) {
        EsmHttpErrorCode esmHttpErrorCode = EsmHttpErrorCode.ENTITY_NOT_FOUND;
        return createResponseEntity(e, HttpStatus.NOT_FOUND, esmHttpErrorCode, locale);
    }

    @ExceptionHandler(EntityExistsException.class)
    private ResponseEntity<?> handleEntityExistsException(EntityExistsException e, Locale locale) {
        EsmHttpErrorCode esmHttpErrorCode = EsmHttpErrorCode.ENTITY_EXISTS;
        return createResponseEntity(e, HttpStatus.CONFLICT, esmHttpErrorCode, locale);
    }

    /**
     * Throw if method argument is invalid. <br>
     * Contain format message: <code>method_name.method_argument_name: message_from_annotation</code> <br>
     * Example: <code>delete.id: validation.id</code>
     *
     * @param e      ConstraintViolationException
     * @param locale User locale
     * @return ResponseEntity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e, Locale locale) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Set<String> validationMessages = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .map(s -> messageSource.getMessage(s, null, locale))
                .collect(Collectors.toSet());

        EsmHttpErrorCode esmHttpErrorCode = EsmHttpErrorCode.INVALID_ARGUMENT_METHOD;
        EsmException esmException = createEsmException(validationMessages, HttpStatus.BAD_REQUEST, esmHttpErrorCode);
        return new ResponseEntity<>(esmException, esmException.getHttpStatus());
    }

    /**
     * Throw if method argument contain <code>@Valid</code> and any variable(s) not valid.<br>
     * Or if method arguments are primitive type(s) and not valid.
     *
     * @param e      BindException
     * @param locale Request locale.
     * @return ResponseEntity
     */
    @ExceptionHandler(BindException.class)
    private ResponseEntity<?> handleConstraintViolationException(BindException e, Locale locale) {
        EsmHttpErrorCode esmHttpErrorCode = EsmHttpErrorCode.ENTITY_VALIDATION;
        return createResponseEntity(e, HttpStatus.BAD_REQUEST, esmHttpErrorCode, locale);
    }

    private ResponseEntity<Object> createResponseEntity(
            Exception exception, HttpStatus httpStatus, EsmHttpErrorCode esmHttpErrorCode, Locale locale) {
        Set<String> validationMessages;

        if (exception instanceof BindException) {
            BindingResult bindingResult = ((BindException) exception).getBindingResult();
            validationMessages = getValidationErrorMessages(bindingResult, locale);
        } else {
            String validationMessage = messageSource.getMessage(exception.getMessage(), null, locale);
            validationMessages = Collections.singleton(validationMessage);
        }

        EsmException esmException = createEsmException(validationMessages, httpStatus, esmHttpErrorCode);
        return new ResponseEntity<>(esmException, httpStatus);
    }

    private EsmException createEsmException(Set<String> validationErrors, HttpStatus httpStatus, EsmHttpErrorCode esmHttpErrorCode) {
        int customErrorCode = generateEsmHttpErrorCode(httpStatus, esmHttpErrorCode);
        EsmExceptionBody esmExceptionBody = new EsmExceptionBody(validationErrors, customErrorCode);
        return new EsmException(httpStatus, esmExceptionBody);
    }

    private int generateEsmHttpErrorCode(HttpStatus httpStatus, EsmHttpErrorCode esmHttpErrorCode) {
        String formatterCustomErrorCode = String.format("%d%02d", httpStatus.value(), esmHttpErrorCode.value);
        return Integer.parseInt(formatterCustomErrorCode);
    }

    private Set<String> getValidationErrorMessages(BindingResult bindingResult, Locale locale) {
        return bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .map(localePropertyKey -> messageSource.getMessage(localePropertyKey, null, locale))
                .collect(Collectors.toSet());
    }

    private enum EsmHttpErrorCode {
        ENTITY_NOT_FOUND(1),
        ENTITY_EXISTS(2),
        ENTITY_VALIDATION(3),
        ENUM_CONVERSION_FAILED(4),
        INVALID_ARGUMENT_METHOD(5);

        private final int value;

        EsmHttpErrorCode(int value) {
            this.value = value;
        }
    }
}