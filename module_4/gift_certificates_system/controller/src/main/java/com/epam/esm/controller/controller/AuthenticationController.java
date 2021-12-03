package com.epam.esm.controller.controller;

import com.epam.esm.controller.util.hateoas.LinkBuilder;
import com.epam.esm.model.dto.AuthenticationUserDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.util.UrlMapping;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.epam.esm.service.security.JwtUtils.Parameter.JWT_HTTP_HEADER_NAME;

@RestController
@RequestMapping(value = UrlMapping.AUTHENTICATION)
@Validated
public class AuthenticationController {
    private final UserService userService;
    private final LinkBuilder<UserDto> userLinkBuilder;

    @Autowired
    public AuthenticationController(LinkBuilder<UserDto> userLinkBuilder, UserServiceImpl userService) {
        this.userLinkBuilder = userLinkBuilder;
        this.userService = userService;
    }

    /**
     * Create user.
     *
     * @param userDto User DTO.
     * @return Created user DTO.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(UrlMapping.SIGN_UP)
    public EntityModel<UserDto> signUp(@Valid @RequestBody UserDto userDto, HttpServletResponse response) {
        UserDto user = userService.create(userDto);
        setJwtIntoHeader(user.getJwt(), response);
        userLinkBuilder.build(user);
        return EntityModel.of(user);
    }

    @PostMapping(UrlMapping.SIGN_IN)
    public EntityModel<UserDto> signIn(@Valid @RequestBody AuthenticationUserDto authenticationUser, HttpServletResponse response) {
        UserDto user = userService.signIn(authenticationUser);
        setJwtIntoHeader(user.getJwt(), response);
        return EntityModel.of(user);
    }

    private void setJwtIntoHeader(String jwt, HttpServletResponse response) {
        response.setHeader(JWT_HTTP_HEADER_NAME.toString(), jwt);
    }
}