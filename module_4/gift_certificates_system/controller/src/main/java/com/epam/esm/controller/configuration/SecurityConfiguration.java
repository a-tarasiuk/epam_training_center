package com.epam.esm.controller.configuration;

import com.epam.esm.controller.filter.JwtFilter;
import com.epam.esm.model.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.epam.esm.model.entity.User.Role.*;
import static com.epam.esm.model.util.UrlMapping.AUTHENTICATION;
import static com.epam.esm.model.util.UrlMapping.GIFT_CERTIFICATES;
import static com.epam.esm.model.util.UrlMapping.ORDERS;

@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtFilter jwtFilter;

    public SecurityConfiguration(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, GIFT_CERTIFICATES + "/**").permitAll()
                .antMatchers(AUTHENTICATION + "/**").anonymous()
                .antMatchers(HttpMethod.POST, ORDERS).hasRole(ROLE_USER.withoutPrefix())
                .antMatchers(HttpMethod.GET).hasRole(ROLE_USER.withoutPrefix())
                .antMatchers("/tags/most-widely").hasRole(ROLE_ADMINISTRATOR.withoutPrefix())
                .anyRequest().hasRole(ROLE_ADMINISTRATOR.withoutPrefix())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}