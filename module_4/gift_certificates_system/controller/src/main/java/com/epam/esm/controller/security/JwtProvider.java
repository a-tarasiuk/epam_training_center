package com.epam.esm.controller.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import static com.epam.esm.model.entity.User.Role;

@Component
public class JwtProvider {
    private String SIGNING_KEY = "esm";
    private final UserDetailsService userService;

    public JwtProvider(UserDetailsService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        SIGNING_KEY = Base64.getEncoder()
                .encodeToString(SIGNING_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String create(String login, Role userRole) {
        Claims claims = Jwts.claims();
        claims.setSubject(login);
        claims.put("login", login);
        claims.put("role", userRole);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String login = getLoginFromJwt(token);
        UserDetails userDetails = userService.loadUserByUsername(login);
        return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, userDetails.getAuthorities());
    }

    public boolean isExistAuthentication(Authentication authentication) {
        return Objects.nonNull(authentication);
    }

    public boolean isValid(String token) {
        return Objects.nonNull(token);
    }

    private String getLoginFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
