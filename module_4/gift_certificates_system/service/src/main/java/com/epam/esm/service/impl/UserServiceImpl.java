package com.epam.esm.service.impl;

import com.epam.esm.model.dto.AuthenticationUserDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.EntityExistingException;
import com.epam.esm.service.exception.EntityNonExistentException;
import com.epam.esm.service.security.JwtUtils;
import com.epam.esm.service.util.PageMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_UNSUPPORTED_OPERATION;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_ID_NOT_FOUND;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_LOGIN_EXISTS;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_LOGIN_NOT_FOUND;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_LOGIN_OR_PASSWORD_INCORRECT;

/**
 * User service implementation.
 */
@Log4j2
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final PageMapper pageMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper, PageMapper pageMapper) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        checkIfUserExistsOrElseThrow(user);

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPasswordEncoded(encodedPassword);

        User createdUser = userRepository.save(user);
        return createUserDtoFromUserWithJwt(createdUser);
    }

    public UserDto signIn(AuthenticationUserDto authenticationUser) {
        String login = authenticationUser.getLogin();
        String password = authenticationUser.getPassword();

        User user = userRepository.findByLogin(login).orElseThrow(
                () -> new EntityNotFoundException(EXCEPTION_USER_LOGIN_OR_PASSWORD_INCORRECT)
        );

        if (passwordEncoder.matches(password, user.getPasswordEncoded())) {
            return createUserDtoFromUserWithJwt(user);
        } else {
            throw new EntityNotFoundException(EXCEPTION_USER_LOGIN_OR_PASSWORD_INCORRECT);
        }
    }

    @Override
    public Page<UserDto> findAll(EsmPagination pagination) {
        Pageable pageable = pageMapper.map(pagination);
        Page<User> users = userRepository.findAll(pageable);
        return pageMapper.map(users, UserDto.class);
    }

    @Override
    public UserDto findById(long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new EntityNonExistentException(EXCEPTION_USER_ID_NOT_FOUND, id));
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException(EXCEPTION_UNSUPPORTED_OPERATION);
    }

    private void checkIfUserExistsOrElseThrow(final User user) {
        String login = user.getLogin();
        userRepository.findByLogin(login).ifPresent(t -> {
            throw new EntityExistingException(EXCEPTION_USER_LOGIN_EXISTS, login);
        });
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        return userRepository.findByLogin(login).orElseThrow(
                () -> new EntityNonExistentException(EXCEPTION_USER_LOGIN_NOT_FOUND, login)
        );
    }

    private UserDto createUserDtoFromUserWithJwt(User user) {
        UserDto foundUser = modelMapper.map(user, UserDto.class);
        String jwt = jwtUtils.createJwt(foundUser.getLogin());
        foundUser.setJwt(jwt);
        return foundUser;
    }
}
