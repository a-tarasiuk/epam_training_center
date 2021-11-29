package com.epam.esm.controller.configuration;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.util.UrlMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.epam.esm.model.entity.User.Role.ROLE_ADMINISTRATOR;
import static com.epam.esm.model.entity.User.Role.ROLE_USER;

@EnableWebSecurity(debug = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtConfiguration jwtConfiguration;

    public SecurityConfiguration(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(UrlMapping.AUTHENTICATION + UrlMapping.SIGN_UP).permitAll()
                .antMatchers("/auth/admin-profile").hasRole(ROLE_ADMINISTRATOR.name())
                .antMatchers("/auth/read-profile").hasRole(ROLE_USER.name())
                .and()
                .formLogin()
                .usernameParameter(User.DefaultUsernameParameter.LOGIN.name().toLowerCase()) // default - username;  let my custom EsmSecurityConfiguration know that "login" is a principal parameter now
                .and()
                .logout().logoutSuccessUrl("/auth/go")
                .and()
                .apply(jwtConfiguration);
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
}