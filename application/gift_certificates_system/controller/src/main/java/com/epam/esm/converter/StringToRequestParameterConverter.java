package com.epam.esm.converter;

import com.epam.esm.util.RequestParameter;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;

public class StringToRequestParameterConverter implements Converter<String, RequestParameter> {
    @Override
    public RequestParameter convert(@NotNull String source) {
        return RequestParameter.valueOf(source.toUpperCase());
    }
}
