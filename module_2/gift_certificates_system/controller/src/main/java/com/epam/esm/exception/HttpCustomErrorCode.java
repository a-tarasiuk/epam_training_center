package com.epam.esm.exception;

import com.fasterxml.jackson.annotation.JsonValue;

enum HttpCustomErrorCode {
    ENTITY_EXISTS(40601),
    ENTITY_NOT_FOUND(40602),
    ENTITY_INVALID(40603),
    ENTITY_INVALID_FIELD(40604),
    ENUM_CONSTANT_NOT_PRESENT(40605),
    COLUMN_NAME_NOT_PRESENT(40606),
    OPERATION_FAILED(40607);

    @JsonValue
    private final int errorCode;

    HttpCustomErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
