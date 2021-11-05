package com.epam.esm.converter;

import com.epam.esm.util.SqlSortOperator;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

import javax.validation.constraints.NotBlank;

public class StringToSqlSortOperatorConverter implements Converter<String, SqlSortOperator> {

    @Override
    public SqlSortOperator convert(@NotNull String source) {
        return EnumUtils.getEnumIgnoreCase(SqlSortOperator.class, source, SqlSortOperator.ASC);
    }
}
