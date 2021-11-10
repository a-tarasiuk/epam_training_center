package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.util.DatabaseColumnName;
import com.epam.esm.util.ParameterName;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

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
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }
}
