package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.service.AbstractService;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.pagination.EsmPagination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User service implementation.
 */
@Service
@Transactional
public class UserServiceImpl implements AbstractService<UserDto> {
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
    public Set<UserDto> findAll(EsmPagination esmPagination) {
        return userDao.findAll(esmPagination, User.class).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public UserDto findById(long id) {
        return userDao.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new EntityNotFoundException(MessagePropertyKey.EXCEPTION_USER_ID_NOT_FOUND));
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    private void checkIfUserExistsOrElseThrow(User user) {
        String login = user.getLogin();
        userDao.findBy(login).ifPresent(t -> {
            throw new EntityExistsException(MessagePropertyKey.EXCEPTION_USER_LOGIN_EXISTS);
        });
    }
}
