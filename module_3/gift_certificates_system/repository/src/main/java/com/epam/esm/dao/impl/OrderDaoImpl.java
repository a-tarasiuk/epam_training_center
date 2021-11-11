package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.util.ParameterName;
import com.epam.esm.util.pagination.EsmPagination;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public Set<Order> findAllBy(User user, EsmPagination esmPagination) {
        int countPages = esmPagination.getPage();
        int elementsOnPage = esmPagination.getSize();

        checkEsmPaginationForValidityOrElseThrow(Order.class, countPages, elementsOnPage);

        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> fromOrder = cq.from(Order.class);
        Predicate condition = cb.equal(fromOrder.get(ParameterName.USER), user);
        cq.select(fromOrder).where(condition);

        return em.createQuery(cq)
                .setFirstResult((countPages - NumberUtils.INTEGER_ONE) * elementsOnPage)
                .setMaxResults(elementsOnPage)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    /**
     * Full query:<br>
     * SELECT o.user FROM Order o GROUP BY o.user ORDER BY SUM(o.price) DESC
     */
    @Override
    public Set<User> findUsersWithHighestCostOfAllOrders() {
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<Order> from = cq.from(Order.class);
        Path<User> user = from.get(ParameterName.USER);

        cq.select(user)
                .orderBy(cb.desc(cb.sum(from.get(ParameterName.PRICE))))
                .groupBy(user);

        return em.createQuery(cq)
                .setMaxResults(NumberUtils.INTEGER_ONE)
                .getResultStream()
                .collect(Collectors.toSet());
    }
}
