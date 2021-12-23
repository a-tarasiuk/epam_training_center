package com.epam.esm.converter;

import com.epam.esm.util.SqlSortOperator;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

public class StringToSqlSortOperatorConverter implements Converter<String, SqlSortOperator> {

    @Override
    public SqlSortOperator convert(@NotNull String source) {
        return SqlSortOperator.valueOf(source.toUpperCase());
    }
}
