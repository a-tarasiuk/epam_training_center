package com.epam.esm.converter;

import com.epam.esm.util.ColumnName;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;

public class StringToColumnNameConverter implements Converter<String, ColumnName> {
    @Override
    public ColumnName convert(@NotNull String source) {
        return ColumnName.valueOf(source.toUpperCase());
    }
}
