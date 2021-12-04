package com.epam.esm.controller.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class ExceptionBody {
    private static final String ERROR_MESSAGE_PROPERTY = "Error message";
    private static final String ERROR_CODE_PROPERTY = "Error esm code";

    @JsonProperty(ERROR_MESSAGE_PROPERTY)
    private final Set<String> errorMessages;

    @JsonProperty(ERROR_CODE_PROPERTY)
    private final int errorCode;
}
