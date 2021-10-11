package com.epam.esm.util;

import org.springframework.core.convert.converter.Converter;
import java.util.Arrays;

/**
 * String to column name enum converter.
 *
 * @see com.epam.esm.util.ColumnName
 */
public class StringToColumnNameEnumConverter implements Converter<String, ColumnName> {
    /**
     * Converting string to column name enum.
     *
     * @param source - String.
     * @return - Column name enum.
     */
    @Override
    public ColumnName convert(String source) {
        ColumnName columnName;

        if (source == null) {
            columnName = ColumnName.WITHOUT_SORTING;
        } else {
            columnName = Arrays.stream(ColumnName.values())
                    .filter(e -> e.name().equalsIgnoreCase(source)).findFirst().orElse(ColumnName.NAME);
        }

        return columnName;
    }
}
