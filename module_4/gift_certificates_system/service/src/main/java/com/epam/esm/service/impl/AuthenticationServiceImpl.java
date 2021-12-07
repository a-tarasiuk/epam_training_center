package com.epam.esm.service.impl;

import com.epam.esm.model.dto.AuthenticationUserDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.AuthenticationService;
import com.epam.esm.service.exception.EntityExistsException;
import com.epam.esm.service.security.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_LOGIN_EXISTS;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_LOGIN_OR_PASSWORD_INCORRECT;

/**
 * Authentication service implementation.
 */
@Log4j2
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder,
                                     ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto signUp(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        checkIfUserExistsOrElseThrow(user);

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPasswordEncoded(encodedPassword);

        User createdUser = userRepository.save(user);

        log.info("User successfully sign up: {}", createdUser);
        return createUserDtoFromUserWithJwt(createdUser);
    }

    public UserDto signIn(AuthenticationUserDto authenticationUser) {
        String login = authenticationUser.getLogin();
        String password = authenticationUser.getPassword();

        User user = userRepository.findByLogin(login).orElseThrow(
                () -> new javax.persistence.EntityNotFoundException(EXCEPTION_USER_LOGIN_OR_PASSWORD_INCORRECT)
        );

        if (passwordEncoder.matches(password, user.getPasswordEncoded())) {
            log.info("User successfully sign in: {}", user);
            return createUserDtoFromUserWithJwt(user);
        } else {
            throw new javax.persistence.EntityNotFoundException(EXCEPTION_USER_LOGIN_OR_PASSWORD_INCORRECT);
        }
    }

    private void checkIfUserExistsOrElseThrow(final User user) {
        String login = user.getLogin();
        userRepository.findByLogin(login).ifPresent(t -> {
            throw new EntityExistsException(EXCEPTION_USER_LOGIN_EXISTS, login);
        });
    }

    private UserDto createUserDtoFromUserWithJwt(User user) {
        UserDto foundUser = modelMapper.map(user, UserDto.class);
        String jwt = jwtUtils.createJwt(foundUser.getLogin());
        foundUser.setJwt(jwt);
        return foundUser;
    }
}
