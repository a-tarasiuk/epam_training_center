package com.epam.esm.util;

import java.util.Locale;

import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_COLUMN_NAME_NOT_FOUND;

public enum ColumnName {
    NAME,
    DESCRIPTION,
    PRICE,
    DURATION,
    CREATE_DATE,
    LAST_UPDATE_DATE;

    public static ColumnName convertFromString(String value) {
        try {
            return valueOf(value.toUpperCase(Locale.US));
        } catch (Exception e) {
            throw new IllegalArgumentException(EXCEPTION_GIFT_CERTIFICATE_COLUMN_NAME_NOT_FOUND);
        }
    }
}
