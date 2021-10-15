package com.epam.esm.util;

import org.springframework.stereotype.Component;

@Component
public final class PropertyKey {
    public static final String MESSAGE_ENTITY_EXISTS_EXCEPTION = "message.entity.exists.exception";
    public static final String MESSAGE_ENTITY_NOT_FOUND_EXCEPTION = "message.entity.notFound.exception";
    public static final String MESSAGE_ENTITY_INVALID_EXCEPTION = "message.entity.invalid.exception";
    public static final String MESSAGE_REQUIRED_FIELDS_EMPTY_EXCEPTION = "message.entity.requiredFieldsEmpty.exception";
    public static final String MESSAGE_NAME_INVALID_EXCEPTION = "message.name.invalid.exception";
    public static final String MESSAGE_DESCRIPTION_INVALID_EXCEPTION = "message.description.invalid.exception";
    public static final String MESSAGE_KEYWORD_INVALID_EXCEPTION = "message.keyword.invalid.exception";
    private PropertyKey() {
    }
}
