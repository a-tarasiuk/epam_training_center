package com.epam.esm.converter;

import com.epam.esm.util.ColumnName;
import com.epam.esm.util.MessagePropertyKey;
import org.springframework.core.convert.converter.Converter;

import javax.validation.constraints.NotBlank;

public class StringToColumnNameConverter implements Converter<String, ColumnName> {
    @Override
    public ColumnName convert(@NotBlank(message = MessagePropertyKey.VALIDATION_DATABASE_COLUMN_NAME_NOT_BLANK) String source) {
        return ColumnName.valueOf(source.toUpperCase());
    }
}