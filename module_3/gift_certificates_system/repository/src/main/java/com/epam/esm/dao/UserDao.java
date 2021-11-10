package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * User DAO layer class.
 * Works with database.
 */
public abstract class UserDao extends AbstractDao<User> {
    public abstract Optional<User> findBy(String login);
}
