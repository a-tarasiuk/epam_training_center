package com.epam.esm.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public class ExceptionInformation {
    private HttpStatus httpStatus;
    private ExceptionBody exceptionBody;
}
