package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.exception.EntityExistingException;
import com.epam.esm.service.exception.EntityNonExistentException;
import com.epam.esm.service.CreateService;
import com.epam.esm.repository.util.EsmPagination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_UNSUPPORTED_OPERATION;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_ID_NOT_FOUND;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_USER_LOGIN_EXISTS;

/**
 * User service implementation.
 */
@Service
public class UserServiceImpl implements CreateService<UserDto> {
    private final ModelMapper modelMapper;
    private final UserDao userDao;

    /**
     * Instantiates a new tag service.
     *
     * @param userDao - Tag DAO layer.
     */
    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserDao userDao) {
        this.modelMapper = modelMapper;
        this.userDao = userDao;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        checkIfUserExistsOrElseThrow(user);
        User createdUser = userDao.create(user);
        return modelMapper.map(createdUser, UserDto.class);
    }

    @Override
    public Set<UserDto> findAll(EsmPagination pagination) {
        return userDao.findAll(pagination, User.class).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public UserDto findById(long id) {
        return userDao.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new EntityNonExistentException(EXCEPTION_USER_ID_NOT_FOUND, id));
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException(EXCEPTION_UNSUPPORTED_OPERATION);
    }

    private void checkIfUserExistsOrElseThrow(User user) {
        String login = user.getLogin();
        userDao.findBy(login).ifPresent(t -> {
            throw new EntityExistingException(EXCEPTION_USER_LOGIN_EXISTS, login);
        });
    }
}
