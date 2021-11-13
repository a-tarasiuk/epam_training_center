package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserPrice;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.ParameterName;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * JPA query:<br>
     * SELECT o.user FROM Order o GROUP BY o.user ORDER BY SUM(o.price) DESC
     */
    @Override
    public Set<UserPrice> findUsersWithHighestCostOfAllOrders() {
        CriteriaQuery<UserPrice> cq = cb.createQuery(UserPrice.class);
        Root<Order> from = cq.from(Order.class);

        Path<User> user = from.get(ParameterName.USER);
        Path<BigDecimal> price = from.get(ParameterName.PRICE);

        Expression<BigDecimal> sumAllOrders = cb.sum(price);

        cq.multiselect(user, sumAllOrders)
                .orderBy(cb.desc(sumAllOrders))
                .groupBy(user);

        return em.createQuery(cq)
                .setMaxResults(NumberUtils.INTEGER_ONE)
                .getResultStream()
                .collect(Collectors.toSet());
    }
}