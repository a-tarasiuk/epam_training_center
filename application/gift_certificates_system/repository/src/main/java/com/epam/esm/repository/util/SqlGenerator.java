package com.epam.esm.repository.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlGenerator {
    private static final char PERCENT_SIGN = '%';
    private static final char DELIMITER = ' ';

    public static String like(String sqlQueryValue) {
        return new StringBuilder()
                .append(PERCENT_SIGN)
                .append(sqlQueryValue)
                .append(PERCENT_SIGN)
                .toString();
    }

    public static String join(String... partOfSql) {
        return Arrays.stream(partOfSql).collect(Collectors.joining(String.valueOf(DELIMITER)));
    }
}
