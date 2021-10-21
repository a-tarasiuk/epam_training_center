package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class EsmException {
    private static final String HTTP_STATUS_HEADER = "HTTP Status";
    private static final String RESPONSE_BODY_HEADER = "Response body";
    private HttpStatus httpStatus;
    private EsmExceptionBody esmExceptionBody;
}
