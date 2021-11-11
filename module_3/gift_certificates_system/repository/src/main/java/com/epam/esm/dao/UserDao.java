package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.Optional;

/**
 * User DAO layer class.
 * Works with database.
 */
public abstract class UserDao extends AbstractDao<User> {
    /**
     * Find user by login.
     *
     * @param login User login.
     * @return Optional of user.
     */
    public abstract Optional<User> findBy(String login);
}
