package com.epam.esm.controller.handler;

import com.epam.esm.controller.util.ExceptionUtils;
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

@Log4j2
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Autowired
    private MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApplicationHttpStatus applicationStatus = ApplicationHttpStatus.ACCESS_DENIED;
        response.setStatus(status.value());

        Locale locale = request.getLocale();
        String key = authException.getMessage();
        String localeMessage = messageSource.getMessage(key, null, locale);

        ExceptionInformation information = ExceptionUtils.generateExceptionInformation(localeMessage, status, applicationStatus);
        response.getWriter().write(information.toString());
    }
}