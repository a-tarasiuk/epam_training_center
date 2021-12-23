package com.epam.esm.repository.dao.impl;

import com.epam.esm.repository.dao.OrderDao;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.repository.util.ParameterName;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_UNSUPPORTED_OPERATION;

/**
 * Order DAO implementation.
 */
@Repository
public class OrderDaoImpl extends OrderDao {

    @Override
    public void delete(Order entity) {
        throw new UnsupportedOperationException(EXCEPTION_UNSUPPORTED_OPERATION);
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(em.find(Order.class, id));
    }

    @Override
    public Optional<Order> findByName(String name) {
        throw new UnsupportedOperationException(EXCEPTION_UNSUPPORTED_OPERATION);
    }

    @Override
    public Set<Order> findAllBy(User user, EsmPagination pagination) {
        validatePaginationOrElseThrow(pagination, Order.class);

        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> root = cq.from(Order.class);
        Predicate condition = cb.equal(root.get(ParameterName.USER), user);
        cq.select(root).where(condition);

        return executeQuery(cq, pagination);
    }
}