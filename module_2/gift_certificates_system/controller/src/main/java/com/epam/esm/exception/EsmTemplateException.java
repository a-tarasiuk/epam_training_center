package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class EsmTemplateException {
    private HttpStatus httpStatus;
    private EsmExceptionBody responseBody;
}
