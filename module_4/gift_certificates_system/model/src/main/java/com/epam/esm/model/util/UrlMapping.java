package com.epam.esm.model.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Url mapping for controllers.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlMapping {

    /**
     * General URLs.
     */
    public static final String ID = "/{id}";
    public static final String ALL_INSIDE = "/**";

    /**
     * URL for Gift certificate entities
     */
    public static final String GIFT_CERTIFICATES = "/gift-certificates";

    /**
     * URL for Tag
     */
    public static final String TAGS = "/tags";
    public static final String MOST_WIDELY_USED_TAG_OF_TOP_USER = "/most-widely-used-of-top-user";

    /**
     * URL only for User.
     */
    public static final String USERS = "/users";

    /**
     * URL for User with Order
     */
    public static final String FIND_ORDER_FOR_USER = "/users/{userId}/orders/{orderId}";
    public static final String CREATE_ORDER_FOR_USER = "users/{userId}/orders";

    /**
     * URL for Order
     */
    public static final String ORDERS = "/orders";

    /**
     * URL for Authentication
     */
    public static final String AUTHENTICATION = "/auth";
    public static final String SIGN_UP = "/sign-up";
    public static final String SIGN_IN = "/sign-in";
    public static final String TOKEN = "/token";
}
