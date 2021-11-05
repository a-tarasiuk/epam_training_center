package com.epam.esm.util;

public final class SqlLikeGenerator {
    private static final char PERCENT_SIGN = '%';

    private SqlLikeGenerator() {
    }

    public static String generate(String sqlQueryValue) {
        return new StringBuilder()
                .append(PERCENT_SIGN)
                .append(sqlQueryValue)
                .append(PERCENT_SIGN)
                .toString();
    }
}
