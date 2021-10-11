package com.epam.esm.util;

/**
 * Url mapping for controllers.
 *
 */
public final class UrlMapping {
    private UrlMapping() {
    }

    /**
     * General URLs.
     */
    public static final String ID = "/{id}";
    public static final String CREATE_ENTITY = "/new";

    /**
     * URL for gift certificate entities.
     */
    public static final String GIFT_CERTIFICATES = "/gift-certificates";

    /**
     * URL for tag entities.
     */
    public static final String TAGS = "/tags";
}
