package com.epam.esm.model.util;

public final class MessagePropertyKey {
    // Validation - General
    public static final String VALIDATION_ID = "validation.id";
    public static final String VALIDATION_DATABASE_COLUMN_NAME_NOT_BLANK = "validation.database.column-name.not-blank";

    // Validation - User
    public static final String VALIDATION_USER_ID = "validation.user.id";
    public static final String VALIDATION_USER_ID_NOT_EMPTY = "validation.user.id.not-empty";
    public static final String VALIDATION_USER_LOGIN = "validation.user.login";
    public static final String VALIDATION_USER_LOGIN_NOT_EMPTY = "validation.user.login.not-empty";
    public static final String VALIDATION_USER_PASSWORD_NOT_EMPTY = "validation.user.password.not-empty";
    public static final String VALIDATION_USER_FIRST_NAME = "validation.user.first-name";
    public static final String VALIDATION_USER_FIRST_NAME_NOT_EMPTY = "validation.user.first-name.not-empty";
    public static final String VALIDATION_USER_LAST_NAME = "validation.user.last-name";
    public static final String VALIDATION_USER_LAST_NAME_NOT_EMPTY = "validation.user.last-name.not-empty";

    // Validation - Tag
    public static final String VALIDATION_TAG_NAME = "validation.tag.name";
    public static final String VALIDATION_TAG_NAME_NOT_EMPTY = "validation.tag.name.not-empty";

    // Validation - Order
    public static final String VALIDATION_ORDER_ID = "validation.order.id";

    // Validation - Gift certificate
    public static final String VALIDATION_GIFT_CERTIFICATE_ID = "validation.gift-certificate.id";
    public static final String VALIDATION_GIFT_CERTIFICATE_ID_NOT_NULL = "validation.gift-certificate.id.not-null";
    public static final String VALIDATION_GIFT_CERTIFICATE_NAME = "validation.gift-certificate.name";
    public static final String VALIDATION_GIFT_CERTIFICATE_NAME_NOT_EMPTY = "validation.gift-certificate.name.not-empty";
    public static final String VALIDATION_GIFT_CERTIFICATE_DESCRIPTION = "validation.gift-certificate.description";
    public static final String VALIDATION_GIFT_CERTIFICATE_DESCRIPTION_NOT_EMPTY = "validation.gift-certificate.description.not-empty";
    public static final String VALIDATION_GIFT_CERTIFICATE_PRICE = "validation.gift-certificate.price";
    public static final String VALIDATION_GIFT_CERTIFICATE_PRICE_MINIMAL = "validation.gift-certificate.price.minimal";
    public static final String VALIDATION_GIFT_CERTIFICATE_PRICE_NOT_NULL = "validation.gift-certificate.price.not-null";
    public static final String VALIDATION_GIFT_CERTIFICATE_DURATION = "validation.gift-certificate.duration";
    public static final String VALIDATION_GIFT_CERTIFICATE_DURATION_NOT_NULL = "validation.gift-certificate.duration.not-null";
    public static final String VALIDATION_GIFT_CERTIFICATE_TAGS_NOT_EMPTY = "validation.gift-certificate.tags.not-empty";
    public static final String VALIDATION_GIFT_CERTIFICATE_KEYWORD_NOT_BLANK = "validation.gift-certificate.keyword.not-blank";

    // Exceptions - User
    public static final String EXCEPTION_USER_ID_NOT_FOUND = "exception.user.id.not-found";
    public static final String EXCEPTION_USER_LOGIN_NOT_FOUND = "exception.user.login.not-found";
    public static final String EXCEPTION_USER_LOGIN_EXISTS = "exception.user.login.exists";

    // Exceptions - Order
    public static final String EXCEPTION_ORDER_ID_NOT_FOUND = "exception.order.id.not-found";
    public static final String EXCEPTION_ORDER_FOR_USER_NOT_FOUND = "exception.order.user.not-found";

    // Exceptions - Tag
    public static final String EXCEPTION_TAG_NOT_NULL = "exception.tag.name.not-null";
    public static final String EXCEPTION_TAG_NAME_EXISTS = "exception.tag.name.exists";
    public static final String EXCEPTION_TAG_NAME_NOT_FOUND = "exception.tag.name.not-found";
    public static final String EXCEPTION_TAG_ID_NOT_FOUND = "exception.tag.id.not-found";

    // Exceptions - Gift certificate
    public static final String EXCEPTION_GIFT_CERTIFICATE_NOT_NULL = "exception.gift-certificate.not-null";
    public static final String EXCEPTION_GIFT_CERTIFICATE_NAME_EXISTS = "exception.gift-certificate.name.exists";
    public static final String EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND = "exception.gift-certificate.id.not-found";
    public static final String EXCEPTION_GIFT_CERTIFICATE_COLUMN_NAME_INCORRECT_VALUE = "exception.gift-certificate.column-name.incorrect-value";
    public static final String EXCEPTION_GIFT_CERTIFICATE_UPDATE_FIELDS_EMPTY = "exception.gift-certificate.update.fields.empty";
    public static final String EXCEPTION_GIFT_CERTIFICATE_WITH_SEARCH_PARAMETERS = "exception.gift-certificate.with-search-parameters.not-found";

    // Exceptions - OrderGenerator.Direction
    public static final String EXCEPTION_ESM_SORT_DIRECTION_INCORRECT_VALUE = "exception.esm-sort.direction.incorrect-value";

    // Exceptions - EsmPagination
    public static final String EXCEPTION_ESM_PAGINATION_PAGE_INCORRECT_VALUE = "exception.esm-pagination.page.incorrect-value";
    public static final String EXCEPTION_ESM_PAGINATION_SIZE_INCORRECT_VALUE = "exception.esm-pagination.size.incorrect-value";
    public static final String EXCEPTION_ESM_PAGINATION_PAGE_OUT_OF_RANGE = "exception.esm-pagination.page.out-of-range";

    // Exceptions - Unsupported operation
    public static final String EXCEPTION_UNSUPPORTED_OPERATION = "exception.unsupported-operation";

    private MessagePropertyKey() {
    }
}
