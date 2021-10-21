package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EsmExceptionBody {
    private final String errorMessage;
    private final int errorCode;
}
