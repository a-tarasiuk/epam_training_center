package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityExistingException;
import com.epam.esm.exception.EntityNonExistentException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.CreateService;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.PageMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_UNSUPPORTED_OPERATION;
import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_USER_ID_NOT_FOUND;
import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_USER_LOGIN_EXISTS;

/**
 * User service implementation.
 */
@Service
public class UserServiceImpl implements CreateService<UserDto> {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PageMapper pageMapper;

    /**
     * Instantiates a new tag service.
     *
     * @param userRepository - Tag DAO layer.
     * @param pageMapper
     */
    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, PageMapper pageMapper) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.pageMapper = pageMapper;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        checkIfUserExistsOrElseThrow(user);
        User createdUser = userRepository.save(user);
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

    private void checkIfUserExistsOrElseThrow(User user) {
        String login = user.getLogin();
        userRepository.findByLogin(login).ifPresent(t -> {
            throw new EntityExistingException(EXCEPTION_USER_LOGIN_EXISTS, login);
        });
    }
}
