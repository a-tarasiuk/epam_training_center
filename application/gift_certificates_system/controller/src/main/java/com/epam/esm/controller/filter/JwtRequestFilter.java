package com.epam.esm.controller.filter;

import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.security.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.esm.service.security.JwtUtils.Parameter.JWT_HTTP_HEADER_NAME;
import static com.epam.esm.service.security.JwtUtils.Parameter.JWT_PREFIX;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public JwtRequestFilter(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeaderValue = getAuthorizationHeader(request);

        if (StringUtils.isNotEmpty(authorizationHeaderValue)) {
            if (StringUtils.startsWith(authorizationHeaderValue, JWT_PREFIX.toString())) {
                String jwt = StringUtils.removeStart(authorizationHeaderValue, JWT_PREFIX.toString()).trim();

                String login = jwtUtils.getLoginFromJwt(jwt);
                User user = (User) userService.loadUserByUsername(login);

                UsernamePasswordAuthenticationToken authentication = jwtUtils.getAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader(JWT_HTTP_HEADER_NAME.toString());
    }
}