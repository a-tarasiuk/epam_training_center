package com.epam.esm.controller.handler;

import com.epam.esm.repository.exception.IncorrectArgumentException;
import com.epam.esm.service.exception.EntityExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.controller.util.ExceptionUtils.ApplicationHttpStatus;
import static com.epam.esm.controller.util.ExceptionUtils.ApplicationHttpStatus.ENUM_CONVERSION_FAILED;

/**
 * ESM exception handler.
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {
    private final ResourceBundleMessageSource messageSource;

    /**
     * Constructor.
     *
     * @param messageSource ResourceBundleMessageSource.
     * @see ResourceBundleMessageSource
     */
    public ApplicationExceptionHandler(ResourceBundleMessageSource messageSource) {
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
        return createResponseEntity(e, HttpStatus.NOT_ACCEPTABLE, ENUM_CONVERSION_FAILED, locale);
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
        return createResponseEntity(e, HttpStatus.NOT_ACCEPTABLE, ApplicationHttpStatus.INVALID_ARGUMENT_METHOD, locale);
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
        return createResponseEntity(e, HttpStatus.CONFLICT, ApplicationHttpStatus.INVALID_ARGUMENT_METHOD, locale);
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
    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    private ResponseEntity<?> handleEntityNotFoundException(javax.persistence.EntityNotFoundException e, Locale locale) {
        return createResponseEntity(e, HttpStatus.NOT_FOUND, ApplicationHttpStatus.ENTITY_NOT_FOUND, locale);
    }

    /**
     * Exception from JPA.
     *
     * @param e      EntityExistsException.
     * @param locale request locale from http header.
     * @return response entity.
     * @see ResponseEntity
     */
    @ExceptionHandler(javax.persistence.EntityExistsException.class)
    private ResponseEntity<?> handleEntityExistsException(javax.persistence.EntityExistsException e, Locale locale) {
        return createResponseEntity(e, HttpStatus.CONFLICT, ApplicationHttpStatus.ENTITY_EXISTS, locale);
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
    private ResponseEntity<?> handleBindException(BindException e, Locale locale) {
        return createResponseEntity(e, HttpStatus.BAD_REQUEST, ApplicationHttpStatus.ENTITY_VALIDATION, locale);
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
    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<?> handleEntityNonExistentException(EntityNotFoundException e, Locale locale) {
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
    @ExceptionHandler(EntityExistsException.class)
    private ResponseEntity<?> handleEntityExistingException(EntityExistsException e, Locale locale) {
        return createResponseEntity(locale, e.getArgument(), e.getMessage());
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

        ExceptionInformation exceptionInformation = createExceptionInformation(validationMessages, HttpStatus.BAD_REQUEST, ApplicationHttpStatus.INVALID_ARGUMENT_METHOD);
        return new ResponseEntity<>(exceptionInformation, exceptionInformation.getHttpStatus());
    }

    private ResponseEntity<?> createResponseEntity(Locale locale, String argument, String message) {
        String validationMessage = String.format(messageSource.getMessage(message, null, locale), argument);
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ExceptionInformation exceptionInformation = createExceptionInformation(Collections.singleton(validationMessage), httpStatus, ApplicationHttpStatus.INVALID_ARGUMENT_METHOD);
        return new ResponseEntity<>(exceptionInformation, httpStatus);
    }

    private ExceptionInformation createExceptionInformation(Set<String> validationErrors, HttpStatus httpStatus, ApplicationHttpStatus applicationHttpStatus) {
        int customErrorCode = generateApplicationHttpStatus(httpStatus, applicationHttpStatus);
        ExceptionBody exceptionBody = new ExceptionBody(validationErrors, customErrorCode);
        return new ExceptionInformation(httpStatus, exceptionBody);
    }

    private ResponseEntity<Object> createResponseEntity(Exception exception,
                                                        HttpStatus httpStatus,
                                                        ApplicationHttpStatus applicationHttpStatus,
                                                        Locale locale) {
        Set<String> validationMessages;

        if (exception instanceof BindException) {
            BindingResult bindingResult = ((BindException) exception).getBindingResult();
            validationMessages = getValidationErrorMessages(bindingResult, locale);
        } else {
            String validationMessage = messageSource.getMessage(exception.getMessage(), null, locale);
            validationMessages = Collections.singleton(validationMessage);
        }

        ExceptionInformation exceptionInformation = createExceptionInformation(validationMessages, httpStatus, applicationHttpStatus);
        return new ResponseEntity<>(exceptionInformation, httpStatus);
    }

    private Set<String> getValidationErrorMessages(BindingResult bindingResult, Locale locale) {
        return bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .map(localePropertyKey -> messageSource.getMessage(localePropertyKey, null, locale))
                .collect(Collectors.toSet());
    }

    private int generateApplicationHttpStatus(HttpStatus httpStatus, ApplicationHttpStatus applicationHttpStatus) {
        String formatterCustomErrorCode = String.format("%d%02d", httpStatus.value(), applicationHttpStatus.value());
        return Integer.parseInt(formatterCustomErrorCode);
    }
}