package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserPrice;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.ParameterName;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.util.MessagePropertyKey.*;

/**
 * User DAO implementation.
 */
@Repository
public class UserDaoImpl extends UserDao {

    @Override
    public User create(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException(EXCEPTION_UNSUPPORTED_OPERATION);
    }

    @Override
    public Optional<User> findBy(String login) {
        Optional<User> optionalUser;

        try {
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> fromUser = cq.from(User.class);
            Predicate condition = cb.equal(fromUser.get(ParameterName.LOGIN), login);
            cq.select(fromUser).where(condition);

            User user = em.createQuery(cq)
                    .getSingleResult();

            optionalUser = Optional.of(user);
        } catch (NoResultException e) {
            optionalUser = Optional.empty();
        }

        return optionalUser;
    }

    @Override
    public Optional<User> findById(long id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        throw new UnsupportedOperationException(EXCEPTION_UNSUPPORTED_OPERATION);
    }


    /**
     * JPA query:<br>
     * SELECT o.user FROM Order o GROUP BY o.user ORDER BY SUM(o.price) DESC LIMIT 1
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
