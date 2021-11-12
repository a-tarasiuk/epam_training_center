package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.util.EsmPagination;

import java.util.Set;

/**
 * Order DAO layer class.
 * Works with database.
 */
public abstract class OrderDao extends AbstractDao<Order> {

    /**
     * Find all orders by user entity (with pagination).
     *
     * @param user          User entity.
     * @param esmPagination Pagination entity.
     * @return Set of user.
     */
    public abstract Set<Order> findAllBy(User user, EsmPagination esmPagination);

    /**
     * Find users with highest cost of all orders.
     *
     * @return Set of users.
     */
    public abstract Set<User> findUsersWithHighestCostOfAllOrders();
}
