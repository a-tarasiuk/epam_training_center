package com.epam.esm.service.impl;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.util.MessagePropertyKey;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.service.CreateService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.EntityExistingException;
import com.epam.esm.service.exception.EntityNonExistentException;
import com.epam.esm.service.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_UNSUPPORTED_OPERATION;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_ID_NOT_FOUND;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_LOGIN_EXISTS;

/**
 * User service implementation.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final PageMapper pageMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, PageMapper pageMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User userForCreating = modelMapper.map(userDto, User.class);
        checkIfUserExistsOrElseThrow(userForCreating);

        String encodedPassword = passwordEncoder.encode(userForCreating.getPasswordEncoded());
        userForCreating.setPasswordEncoded(encodedPassword);

        // todo set token

        User createdUser = userRepository.save(userForCreating);
        return modelMapper.map(createdUser, UserDto.class);
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
                () -> new EntityNonExistentException(MessagePropertyKey.EXCEPTION_USER_LOGIN_NOT_FOUND, login)
        );
    }
}
