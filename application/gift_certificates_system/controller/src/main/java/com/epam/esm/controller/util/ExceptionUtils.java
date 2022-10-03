package com.epam.esm.controller.util;

import com.epam.esm.controller.handler.ExceptionBody;
import com.epam.esm.controller.handler.ExceptionInformation;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class ExceptionUtils {
    public static ExceptionInformation generateExceptionInformation(String localeMessage,
                                                                    HttpStatus httpStatus,
                                                                    ApplicationHttpStatus applicationHttpStatus) {
        int customErrorCode = generateApplicationHttpStatus(httpStatus, applicationHttpStatus);
        ExceptionBody exceptionBody = new ExceptionBody(Collections.singleton(localeMessage), customErrorCode);
        return new ExceptionInformation(httpStatus, exceptionBody);
    }

    private static int generateApplicationHttpStatus(HttpStatus httpStatus, ApplicationHttpStatus applicationHttpStatus) {
        String formatterCustomErrorCode = String.format("%d%02d", httpStatus.value(), applicationHttpStatus.value);
        return Integer.parseInt(formatterCustomErrorCode);
    }

    public enum ApplicationHttpStatus {
        ENTITY_NOT_FOUND(1),
        ENTITY_EXISTS(2),
        ENTITY_VALIDATION(3),
        ENUM_CONVERSION_FAILED(4),
        INVALID_ARGUMENT_METHOD(5),
        ACCESS_DENIED(6),
        FORBIDDEN(7);

        private final int value;

        ApplicationHttpStatus(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}