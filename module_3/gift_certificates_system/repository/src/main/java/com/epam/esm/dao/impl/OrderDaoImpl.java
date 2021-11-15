package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.ParameterName;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;
import java.util.Set;

/**
 * Order DAO implementation.
 */
@Repository
public class OrderDaoImpl extends OrderDao {
    @Override
    public Order create(Order order) {
        em.persist(order);
        return order;
    }

    @Override
    public void delete(Order entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(em.find(Order.class, id));
    }

    @Override
    public Optional<Order> findByName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Order> findAllBy(User user, EsmPagination pagination) {
        validatePaginationOrElseThrow(pagination, Order.class);

        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);
        Predicate condition = cb.equal(order.get(ParameterName.USER), user);
        cq.select(order).where(condition);

        return executeQuery(cq, pagination);
    }

}