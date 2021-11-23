package com.epam.esm.converter;

import com.epam.esm.util.SqlSortOperator;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

public class StringToSqlSortOperatorConverter implements Converter<String, SqlSortOperator> {

    @Override
    public SqlSortOperator convert(@NotNull String source) {
        return EnumUtils.getEnumIgnoreCase(SqlSortOperator.class, source, SqlSortOperator.ASC);
    }
}
