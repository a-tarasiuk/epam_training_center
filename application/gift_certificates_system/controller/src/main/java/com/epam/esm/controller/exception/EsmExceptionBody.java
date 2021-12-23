package com.epam.esm.controller.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class EsmExceptionBody {
    private static final String ERROR_MESSAGE_PROPERTY = "Error message";
    private static final String ERROR_CODE_PROPERTY = "Error esm code";

    @JsonProperty(ERROR_MESSAGE_PROPERTY)
    private final Set<String> errorMessages;

    @JsonProperty(ERROR_CODE_PROPERTY)
    private final int errorCode;
}
