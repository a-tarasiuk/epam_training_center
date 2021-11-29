package com.epam.esm.controller.filter;

import com.epam.esm.controller.security.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final static String BEARER_PREFIX = "Bearer_";
    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String tokenFromRequest = getJwtFromHttpHeader((HttpServletRequest) servletRequest);

        if (jwtProvider.isValid(tokenFromRequest)) {
            Authentication authentication = jwtProvider.getAuthentication(tokenFromRequest);

            if(jwtProvider.isExistAuthentication(authentication)) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getJwtFromHttpHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }
}
