package com.epam.esm.util;

public final class DtoRegexValidator {
    private static final String OR_NULL = "|null";

    // User DTO
    public static final String USER_NAME = "^\\p{Lu}\\p{Ll}{1,50}$";
    public static final String USER_NAME_UPDATE = "^(\\p{Lu}\\p{Ll}{1,50})?$";

    // Tag DTO
    public static final String TAG_NAME = "^\\p{Lu}\\p{Ll}{1,50}$";
    public static final String TAG_NAME_UPDATE = "^\\p{Lu}\\p{Ll}{1,50}$";

    // Gift certificate DTO
    public static final String GIFT_CERTIFICATE_NAME = "^\\p{Lu}\\p{L}{1,50}$";
    public static final String GIFT_CERTIFICATE_NAME_UPDATE = "^\\p{Lu}\\p{L}{1,50}$";
    public static final String GIFT_CERTIFICATE_DESCRIPTION = "^\\p{Lu}[\\p{L}\\p{Zs}]{1,200}[^\\p{Z}]$";
    public static final String GIFT_CERTIFICATE_DESCRIPTION_UPDATE = "^\\p{Lu}[\\p{L}\\p{Zs}]{1,200}[^\\p{Z}]$";

    private DtoRegexValidator() {}
}
