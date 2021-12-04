package com.epam.esm.controller.handler;

import com.epam.esm.controller.util.ExceptionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.epam.esm.controller.util.ExceptionUtils.ApplicationHttpStatus;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_AUTHORIZATION_ACCESS_DENIED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {
    @Autowired
    private MessageSource messageSource;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApplicationHttpStatus applicationStatus = ApplicationHttpStatus.FORBIDDEN;

        response.setStatus(status.value());
        response.setContentType(APPLICATION_JSON_VALUE);

        Locale locale = request.getLocale();
        String localeMessage = messageSource.getMessage(EXCEPTION_AUTHORIZATION_ACCESS_DENIED, null, locale);

        ExceptionInformation information = ExceptionUtils.generateExceptionInformation(localeMessage, status, applicationStatus);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(information));
    }
}
