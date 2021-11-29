package com.epam.esm.controller.controller;

import com.epam.esm.controller.security.JwtProvider;
import com.epam.esm.controller.util.hateoas.LinkBuilder;
import com.epam.esm.model.dto.AuthenticationUserDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.util.UrlMapping;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = UrlMapping.AUTHENTICATION)
@Validated
public class AuthenticationController {
    private final LinkBuilder<UserDto> userLinkBuilder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(LinkBuilder<UserDto> userLinkBuilder, AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserServiceImpl userService) {
        this.userLinkBuilder = userLinkBuilder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
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
    public EntityModel<UserDto> signUp(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.create(userDto);
        userLinkBuilder.build(user);
        return EntityModel.of(user);
    }

    // return only token + timestamp
    @PostMapping(UrlMapping.SIGN_IN)
    public EntityModel<String> signIn(AuthenticationUserDto authenticationUser) {
        String login = authenticationUser.getLogin();
        String password = authenticationUser.getPasswordEncoded();

        User user = (User) userService.loadUserByUsername(login);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        String token = jwtProvider.create(login, user.getRole());

        return EntityModel.of(token);
    }

    @PostMapping(UrlMapping.LOGOUT)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler handler = new SecurityContextLogoutHandler();
        handler.logout(request, response, null);
    }

    @GetMapping("/go")
    public String afterAuthentication(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Hello, " + principal.getName();
    }

    @GetMapping("/read-profile")
    public String pageForReadProfile() {
        return "Page for read profile";
    }

    @GetMapping("/admin-profile")
    public String pageForAdminProfile() {
        return "Page only for admin profile";
    }
}