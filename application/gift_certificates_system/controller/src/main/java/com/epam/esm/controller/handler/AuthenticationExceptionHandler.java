package com.epam.esm.controller.handler;

import com.epam.esm.controller.util.ExceptionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.epam.esm.controller.util.ExceptionUtils.ApplicationHttpStatus;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_AUTHENTICATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApplicationHttpStatus applicationStatus = ApplicationHttpStatus.ACCESS_DENIED;

        response.setStatus(status.value());
        response.setContentType(APPLICATION_JSON_VALUE);

        Locale locale = request.getLocale();
        String localeMessage = messageSource.getMessage(EXCEPTION_AUTHENTICATION, null, locale);

        ExceptionInformation information = ExceptionUtils.generateExceptionInformation(localeMessage, status, applicationStatus);
        response.getWriter().write(objectMapper.writeValueAsString(information));
    }
}