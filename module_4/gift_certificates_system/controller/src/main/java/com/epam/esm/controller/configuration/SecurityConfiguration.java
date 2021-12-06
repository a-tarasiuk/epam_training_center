package com.epam.esm.controller.configuration;

import com.epam.esm.controller.filter.JwtRequestFilter;
import com.epam.esm.controller.handler.AccessDeniedExceptionHandler;
import com.epam.esm.controller.handler.AuthenticationExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.epam.esm.model.entity.User.Role.ROLE_ADMINISTRATOR;
import static com.epam.esm.model.entity.User.Role.ROLE_USER;
import static com.epam.esm.model.util.UrlMapping.ALL_INSIDE;
import static com.epam.esm.model.util.UrlMapping.AUTHENTICATION;
import static com.epam.esm.model.util.UrlMapping.GIFT_CERTIFICATES;
import static com.epam.esm.model.util.UrlMapping.ORDER_FOR_USER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtRequestFilter jwtFilter;
    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public SecurityConfiguration(JwtRequestFilter jwtFilter, ResourceBundleMessageSource messageSource) {
        this.jwtFilter = jwtFilter;
        this.messageSource = messageSource;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Check authorization exceptions.
     *
     * @return AccessDeniedHandler
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedExceptionHandler();
    }

    /**
     * Check JWT and authentication exceptions.
     *
     * @return AuthenticationEntryPoint.
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationExceptionHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(GET, GIFT_CERTIFICATES + ALL_INSIDE).permitAll()
                .antMatchers(GET).hasAnyRole(ROLE_USER.withoutPrefix(), ROLE_ADMINISTRATOR.withoutPrefix())
                .antMatchers(DELETE).hasRole(ROLE_ADMINISTRATOR.withoutPrefix())
                .antMatchers(PATCH).hasRole(ROLE_ADMINISTRATOR.withoutPrefix())
                .antMatchers(AUTHENTICATION + "/**").anonymous()
                .antMatchers(POST, ORDER_FOR_USER).hasAnyRole(ROLE_USER.withoutPrefix(), ROLE_ADMINISTRATOR.withoutPrefix())
                .anyRequest().hasRole(ROLE_ADMINISTRATOR.withoutPrefix())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());
    }
}