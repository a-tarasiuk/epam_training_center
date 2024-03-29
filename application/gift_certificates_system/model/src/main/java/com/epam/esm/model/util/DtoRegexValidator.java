package com.epam.esm.model.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DtoRegexValidator {
    public static final String USER_LOGIN = "^[\\p{Ll}\\p{Lu}]{4,50}$";
    public static final String USER_NAME = "^\\p{Lu}\\p{Ll}{1,50}$";

    public static final String TAG_NAME = "^\\p{Lu}\\p{Ll}{1,50}$";

    public static final String GIFT_CERTIFICATE_NAME = "^\\p{Lu}\\p{L}{1,50}$";
    public static final String GIFT_CERTIFICATE_DESCRIPTION = "^\\p{Lu}[\\p{L}\\p{Zs}]{1,200}[^\\p{Z}]$";
}
