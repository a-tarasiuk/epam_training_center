package com.epam.esm.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class ExceptionBody {
    private final Set<String> errorMessages;
    private final int errorCode;
}
