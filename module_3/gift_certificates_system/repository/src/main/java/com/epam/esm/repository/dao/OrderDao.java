package com.epam.esm.repository.dao;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.util.EsmPagination;

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
     * @param pagination Pagination entity.
     * @return Set of user.
     */
    public abstract Set<Order> findAllBy(User user, EsmPagination pagination);
}
