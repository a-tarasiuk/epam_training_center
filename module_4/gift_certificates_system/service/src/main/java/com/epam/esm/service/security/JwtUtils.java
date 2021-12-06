package com.epam.esm.service.security;

import com.epam.esm.service.exception.IncorrectJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_JWT_EXPIRED;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_JWT_MALFORMED;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_JWT_UNSUPPORTED;

@Component
@Log4j2
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
            throw new IncorrectJwtException(EXCEPTION_JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new IncorrectJwtException(EXCEPTION_JWT_UNSUPPORTED);
        } catch (MalformedJwtException e) {
            throw new IncorrectJwtException(EXCEPTION_JWT_MALFORMED);
        }
    }

    private String encodeToBase64(String value) {
        return Base64.getEncoder()
                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
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
}
