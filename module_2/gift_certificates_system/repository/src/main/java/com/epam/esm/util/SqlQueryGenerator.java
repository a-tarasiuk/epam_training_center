package com.epam.esm.util;

import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.StringJoiner;

@Log4j2
public final class SqlQueryGenerator {
    private static final String PERCENT_MARK = "%";
    private static final String EQUALS_MARK = " = :";
    private static final String DELIMITER_MARK = ", ";
    private static final String UPDATE_GIFT_CERTIFICATE_SET_SQL = "UPDATE gift_certificate SET ";
    private static final String WHERE_ID = " WHERE id = :id";

    private SqlQueryGenerator() {
    }

    public static String forLikeOperator(String sqlQueryValue) {
        StringBuilder sb = new StringBuilder(PERCENT_MARK)
                .append(sqlQueryValue)
                .append(PERCENT_MARK);

        log.debug("SQL query for LIKE operator was generated: {}.", sb);
        return sb.toString();
    }

    public static String forUpdateGiftCertificate(Map<String, Object> fields) {
        StringJoiner joiner = new StringJoiner(DELIMITER_MARK, UPDATE_GIFT_CERTIFICATE_SET_SQL, WHERE_ID);

        fields.entrySet().stream()
                .filter(field -> !field.getKey().equalsIgnoreCase(ColumnName.ID.name()))
                        .forEach(field -> {
                            String fieldName = field.getKey();
                            joiner.add(fieldName + EQUALS_MARK + fieldName);
                        });
        log.debug("SQL query for update gift certificate command was generated '{}'.", joiner);
        return joiner.toString();
    }
}
