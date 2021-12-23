package com.epam.esm.model.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Url mapping for controllers.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlMapping {
    public static final String ID = "/{id}";
    public static final String ALL_INSIDE = "/**";

    public static final String GIFT_CERTIFICATES = "/gift-certificates";

    public static final String TAGS = "/tags";
    public static final String MOST_WIDELY_USED_TAG_OF_TOP_USER = "/most-widely-used-of-top-user";

    public static final String USERS = "/users";

    public static final String FIND_ORDER_FOR_USER = "/users/{userId}/orders/{orderId}";
    public static final String ORDER_FOR_USER = "/users/{userId}/orders";

    public static final String ORDERS = "/orders";
    public static final String FIND_ORDERS_BY_ID = "/orders/{id}";

    public static final String AUTHENTICATION = "/auth";
    public static final String SIGN_UP = "/sign-up";
    public static final String SIGN_IN = "/sign-in";
}
