package com.epam.esm.controller.converter;

import com.epam.esm.controller.util.RequestParameter;
import org.springframework.core.convert.converter.Converter;

import javax.validation.constraints.NotBlank;

public class StringToRequestParameterConverter implements Converter<String, RequestParameter> {
    @Override
    public RequestParameter convert(@NotBlank String source) {
        return RequestParameter.valueOf(source.toUpperCase());
    }
}
