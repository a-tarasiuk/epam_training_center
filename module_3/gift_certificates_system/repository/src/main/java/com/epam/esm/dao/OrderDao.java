package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.util.pagination.EsmPagination;

import java.util.Optional;
import java.util.Set;

/**
 * Order DAO layer class.
 * Works with database.
 */
public abstract class OrderDao extends AbstractDao<Order> {

    public abstract Set<Order> findAllBy(User user, EsmPagination esmPagination);

    public abstract Set<User> findUsersWithHighestCostOfAllOrders();
}
