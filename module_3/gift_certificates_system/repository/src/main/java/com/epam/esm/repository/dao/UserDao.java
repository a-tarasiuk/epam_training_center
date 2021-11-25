package com.epam.esm.repository.dao;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.pojo.UserInformation;
import com.epam.esm.repository.dao.AbstractDao;

import java.util.Optional;
import java.util.Set;

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

    /**
     * Find users with the highest cost of all orders.
     *
     * @return Set of users.
     */
    public abstract Set<UserInformation> findUsersWithHighestCostOfAllOrders();
}
