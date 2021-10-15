package com.epam.esm.util;

import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Log4j2
public final class SqlQueryGenerator {
    private static final String FIND_ALL_GIFT_CERTIFICATES_SQL
            = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate";
    private static final String UPDATE_GIFT_CERTIFICATE_SET_SQL = "UPDATE gift_certificate SET ";

    private static final char SPACE_SIGN = ' ';
    private static final char PERCENT_SIGN = '%';
    private static final char EQUALS_SIGN = '=';
    private static final String EQUALS_QUESTION_MARK = "= ?";
    private static final String COMMA_SIGN = ",";
    private static final String EQUALS_MARK = " = :";

    private static final String WHERE_ID = " WHERE id = :id";
    private static final String ORDER_BY = " ORDER BY";

    private SqlQueryGenerator() {
    }

    public static String forLikeOperator(String sqlQueryValue) {
        StringBuilder sb = new StringBuilder()
                .append(PERCENT_SIGN)
                .append(sqlQueryValue)
                .append(PERCENT_SIGN);

        log.info("SQL query for LIKE operator was generated: {}.", sb);
        return sb.toString();
    }

    public static String update(Map<String, Object> fields) {
        StringJoiner joiner = new StringJoiner(COMMA_SIGN, UPDATE_GIFT_CERTIFICATE_SET_SQL, WHERE_ID);

        fields.entrySet().stream()
                .filter(field -> !field.getKey().equalsIgnoreCase(ColumnName.ID.name()))
                .forEach(field -> {
                    String fieldName = field.getKey();
                    joiner.add(fieldName + EQUALS_MARK + fieldName);
                });

        log.info("Generated SQL query '{}'.", joiner);
        return joiner.toString();
    }

    public static String findAll() {
        return FIND_ALL_GIFT_CERTIFICATES_SQL;
    }

    public static String findAllSorted(Set<ColumnName> orderBy) {
        String concatenatedColumnNames = createStringFromSet(orderBy);
        return new StringJoiner(String.valueOf(SPACE_SIGN))
                .add(FIND_ALL_GIFT_CERTIFICATES_SQL)
                .add(ORDER_BY)
                .add(concatenatedColumnNames)
                .toString();
    }

    public static String findAllSorted(Set<ColumnName> orderBy, SqlSortOperator sqlSortOperator) {
        String concatenated = orderBy.stream()
                .map(columnName -> new StringJoiner(String.valueOf(SPACE_SIGN)).add(columnName.name()).add(sqlSortOperator.name()).toString())
                .collect(Collectors.joining(COMMA_SIGN));

        return new StringJoiner(String.valueOf(SPACE_SIGN))
                .add(FIND_ALL_GIFT_CERTIFICATES_SQL)
                .add(ORDER_BY)
                .add(concatenated)
                .toString();
    }

    private static String createStringFromSet(Set<ColumnName> values) {
        return values.stream()
                .map(Enum::name)
                .collect(Collectors.joining(COMMA_SIGN));
    }
}
