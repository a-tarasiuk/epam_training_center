package com.epam.esm.service.impl;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.util.PageMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_UNSUPPORTED_OPERATION;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_ID_NOT_FOUND;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_LOGIN_NOT_FOUND;

/**
 * User service implementation.
 */
@Log4j2
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PageMapper pageMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PageMapper pageMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    public Page<UserDto> findAll(EsmPagination pagination) {
        Pageable pageable = pageMapper.map(pagination);
        Page<User> users = userRepository.findAll(pageable);

        log.info("Total number of users {} found.", users.getTotalElements());
        return pageMapper.map(users, UserDto.class);
    }

    @Override
    public UserDto findById(long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new EntityNotFoundException(EXCEPTION_USER_ID_NOT_FOUND, id));
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException(EXCEPTION_UNSUPPORTED_OPERATION);
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        return userRepository.findByLogin(login).orElseThrow(
                () -> new EntityNotFoundException(EXCEPTION_USER_LOGIN_NOT_FOUND, login)
        );
    }
}
