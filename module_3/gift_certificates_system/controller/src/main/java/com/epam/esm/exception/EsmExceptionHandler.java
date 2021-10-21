package com.epam.esm.exception;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ControllerAdvice
public class EsmExceptionHandler extends ResponseEntityExceptionHandler {
    private final ResourceBundleMessageSource messageSource;

    public EsmExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private enum EsmHttpErrorCode {
        CUSTOM(01);

        private final int value;

        EsmHttpErrorCode(int value) {
            this.value = value;
        }
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handle(HttpServletRequest request, Throwable throwable, Locale locale) {
        HttpStatus httpStatus = getHttpStatus(request);
        String exceptionMessage = messageSource.getMessage(throwable.getMessage(), null, locale);
        EsmExceptionBody exceptionBody = new EsmExceptionBody(exceptionMessage, EsmHttpErrorCode.CUSTOM.value);
        EsmException exception = new EsmException(httpStatus, exceptionBody);
        return new ResponseEntity<>(exception, httpStatus);
    }

    private HttpStatus getHttpStatus(HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.resolve(code);
        return (httpStatus != null) ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
