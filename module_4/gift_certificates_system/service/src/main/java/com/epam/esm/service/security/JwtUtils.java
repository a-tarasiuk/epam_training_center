package com.epam.esm.service.security;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;

import static com.epam.esm.model.util.MessagePropertyKey.*;

@Component
public class JwtUtils {
    private static final String SIGNING_KEY = "Esm secret key";

    public String createJwt(String login) {
        LocalDate current = LocalDate.now();
        String base64 = encodeToBase64(SIGNING_KEY);

        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(java.sql.Date.valueOf(current))
                .signWith(SignatureAlgorithm.HS256, base64)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, userDetails.getAuthorities());
    }

    public String getLoginFromJwt(String jwt) {
        String base64 = encodeToBase64(SIGNING_KEY);

        try {
            return Jwts.parser()
                    .setSigningKey(base64)
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            throw new AccessDeniedException(EXCEPTION_JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new AccessDeniedException(EXCEPTION_JWT_UNSUPPORTED);
        } catch (MalformedJwtException e) {
            throw new AccessDeniedException(EXCEPTION_JWT_MALFORMED);
        } catch (SignatureException e) {
            throw new AccessDeniedException(EXCEPTION_JWT_SIGNATURE);
        }
    }

    public enum Parameter {
        JWT_HTTP_HEADER_NAME("Authorization"),
        JWT_PREFIX("Bearer");

        private final String value;

        Parameter(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private String encodeToBase64(String value) {
        return Base64.getEncoder()
                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
}
