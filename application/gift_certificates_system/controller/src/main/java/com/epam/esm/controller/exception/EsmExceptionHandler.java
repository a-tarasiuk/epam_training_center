package com.epam.esm.controller.exception;

import com.epam.esm.repository.exception.IncorrectArgumentException;
import com.epam.esm.service.exception.EntityExistingException;
import com.epam.esm.service.exception.EntityNonExistentException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ESM exception handler.
 */
@RestControllerAdvice
public class EsmExceptionHandler {
    private final ResourceBundleMessageSource messageSource;

    /**
     * Constructor.
     *
     * @param messageSource ResourceBundleMessageSource.
     * @see ResourceBundleMessageSource
     */
    public EsmExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Throw this exception if class with implements <code>Converter<String, ColumnName></code>.
     *
     * @param e      exception.
     * @param locale request locale from http header.
     * @return response entity.
     * @see ResponseEntity
     */
    @ExceptionHandler(ConversionFailedException.class)
    private ResponseEntity<?> handleConversionFailedException(RuntimeException e, Locale locale) {
        EsmHttpErrorCode esmHttpErrorCode = EsmHttpErrorCode.ENUM_CONVERSION_FAILED;
        return createResponseEntity(e, HttpStatus.NOT_ACCEPTABLE, esmHttpErrorCode, locale);
    }


    /**
     * Thrown to indicate that a method has been passed an illegal or inappropriate argument.
     *
     * @param e      UnsupportedOperationException.
     * @param locale request locale from http header.
     * @return response entity.
     * @see ResponseEntity
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    private ResponseEntity<?> handleUnsupportedOperationException(UnsupportedOperationException e, Locale locale) {
        EsmHttpErrorCode esmHttpErrorCode = EsmHttpErrorCode.INVALID_ARGUMENT_METHOD;
        return createResponseEntity(e, HttpStatus.NOT_ACCEPTABLE, esmHttpErrorCode, locale);
    }

    /**
     * Thrown to indicate that a method has been passed an illegal or inappropriate argument.
     *
     * @param e      IllegalArgumentException.
     * @param locale request locale from http header.
     * @return response entity.
     * @see ResponseEntity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e, Locale locale) {
        EsmHttpErrorCode esmHttpErrorCode = EsmHttpErrorCode.INVALID_ARGUMENT_METHOD;
        return createResponseEntity(e, HttpStatus.CONFLICT, esmHttpErrorCode, locale);
    }

    /**
     * Custom ESM exception with parameter.<br>
     * Throw to indicate that a method has been passed an illegal or inappropriate argument.
     *
     * @param e      IncorrectArgumentException.
     * @param locale request locale from http header.
     * @return response entity.
     * @see ResponseEntity
     */
    @ExceptionHandler(IncorrectArgumentException.class)
    private ResponseEntity<?> handleIncorrectArgumentException(IncorrectArgumentException e, Locale locale) {
        return createResponseEntity(locale, e.getArgument(), e.getMessage());
    }

    /**
     * Custom ESM exception with parameter.<br>
     * Throw to indicate that the entity not found in the database.
     *
     * @param e      EntityNonExistentException.
     * @param locale request locale from http header.
     * @return response entity.
     * @see ResponseEntity
     */
    @ExceptionHandler(EntityNonExistentException.class)
    private ResponseEntity<?> handleEntityNonExistentException(EntityNonExistentException e, Locale locale) {
        return createResponseEntity(locale, e.getArgument(), e.getMessage());
    }

    /**
     * Custom ESM exception with parameter.<br>
     * Throw to indicate that the entity already existing in the database.
     *
     * @param e      EntityExistingException
     * @param locale request locale from http header.
     * @return response entity.
     * @see ResponseEntity
     */
    @ExceptionHandler(EntityExistingException.class)
    private ResponseEntity<?> handleEntityExistingException(EntityExistingException e, Locale locale) {
        return createResponseEntity(locale, e.getArgument(), e.getMessage());
    }

    /**
     * Exception from JPA.<br>
     * Thrown by the persistence provider when an entity reference obtained by EntityManager.getReference
     * is accessed but the entity does not exist. Thrown when <code>EntityManager.refresh</code> is called and the object
     * no longer exists in the database. Thrown when <code>EntityManager.lock</code> is used with pessimistic locking
     * is used and the entity no longer exists in the database.
     * The current transaction, if one is active and the persistence context has been joined to it, will be marked for rollback.
     *
     * @param e      EntityNotFoundException.
     * @param locale request locale from http header.
     * @return response entity.
     * @see ResponseEntity
     */
    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e, Locale locale) {
        EsmHttpErrorCode esmHttpErrorCode = EsmHttpErrorCode.ENTITY_NOT_FOUND;
        return createResponseEntity(e, HttpStatus.NOT_FOUND, esmHttpErrorCode, locale);
    }

    /**
     * Exception from JPA.
     *
     * @param e      EntityExistsException.
     * @param locale request locale from http header.
     * @return response entity.
     * @see ResponseEntity
     */
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
     * @see ResponseEntity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e, Locale locale) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Set<String> validationMessages = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .map(s -> messageSource.getMessage(s, null, locale))
                .collect(Collectors.toSet());

        EsmHttpErrorCode esmHttpErrorCode = EsmHttpErrorCode.INVALID_ARGUMENT_METHOD;
        EsmExceptionInformation esmExceptionInformation = createEsmException(validationMessages, HttpStatus.BAD_REQUEST, esmHttpErrorCode);
        return new ResponseEntity<>(esmExceptionInformation, esmExceptionInformation.getHttpStatus());
    }

    /**
     * Throw if method argument contain <code>@Valid</code> and any variable(s) not valid.<br>
     * Or if method arguments are primitive type(s) and not valid.
     *
     * @param e      BindException
     * @param locale Request locale.
     * @return ResponseEntity
     * @see ResponseEntity
     */
    @ExceptionHandler(BindException.class)
    private ResponseEntity<?> handleConstraintViolationException(BindException e, Locale locale) {
        EsmHttpErrorCode esmHttpErrorCode = EsmHttpErrorCode.ENTITY_VALIDATION;
        return createResponseEntity(e, HttpStatus.BAD_REQUEST, esmHttpErrorCode, locale);
    }

    /**
     * Build response entity for custom ESM exceptions.
     *
     * @param locale   locale.
     * @param argument exception argument.
     * @param message  key from message properties.
     * @return response entity.
     * @see ResponseEntity
     */
    private ResponseEntity<?> createResponseEntity(Locale locale, String argument, String message) {
        String validationMessage = String.format(messageSource.getMessage(message, null, locale), argument);
        EsmHttpErrorCode httpErrorCode = EsmHttpErrorCode.INVALID_ARGUMENT_METHOD;
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        EsmExceptionInformation esmExceptionInformation = createEsmException(Collections.singleton(validationMessage), httpStatus, httpErrorCode);
        return new ResponseEntity<>(esmExceptionInformation, httpStatus);
    }

    private ResponseEntity<Object> createResponseEntity(Exception exception,
                                                        HttpStatus httpStatus,
                                                        EsmHttpErrorCode esmHttpErrorCode,
                                                        Locale locale) {
        Set<String> validationMessages;

        if (exception instanceof BindException) {
            BindingResult bindingResult = ((BindException) exception).getBindingResult();
            validationMessages = getValidationErrorMessages(bindingResult, locale);
        } else {
            String validationMessage = messageSource.getMessage(exception.getMessage(), null, locale);
            validationMessages = Collections.singleton(validationMessage);
        }

        EsmExceptionInformation esmExceptionInformation = createEsmException(validationMessages, httpStatus, esmHttpErrorCode);
        return new ResponseEntity<>(esmExceptionInformation, httpStatus);
    }

    private EsmExceptionInformation createEsmException(Set<String> validationErrors, HttpStatus httpStatus, EsmHttpErrorCode esmHttpErrorCode) {
        int customErrorCode = generateEsmHttpErrorCode(httpStatus, esmHttpErrorCode);
        EsmExceptionBody esmExceptionBody = new EsmExceptionBody(validationErrors, customErrorCode);
        return new EsmExceptionInformation(httpStatus, esmExceptionBody);
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