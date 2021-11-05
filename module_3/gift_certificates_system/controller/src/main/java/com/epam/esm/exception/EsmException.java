package com.epam.esm.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class EsmException {
    private static final String HTTP_STATUS_PROPERTY = "HTTP Status";
    private static final String RESPONSE_BODY_PROPERTY = "Response body";

    @JsonProperty(HTTP_STATUS_PROPERTY)
    private HttpStatus httpStatus;

    @JsonProperty(RESPONSE_BODY_PROPERTY)
    private EsmExceptionBody esmExceptionBody;
}
