package com.epam.esm.dao;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

/**
 * Entity abstract DAO layer class.<br>
 * Works with database.
 *
 * @param <T> Entity.
 */
public abstract class AbstractDao<T> extends FindAllDao<T> {
    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected CriteriaBuilder cb;

    /**
     * Create an entity in the database.
     *
     * @param entity - Entity being created.
     * @return - Created entity.
     */
    public abstract T create(T entity);

    /**
     * Delete an entity in the database.
     */
    public void delete(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    /**
     * Finding an entity by its ID.
     *
     * @param id - Entity ID.
     * @return - Optional of found entity.
     */
    public abstract Optional<T> findById(long id);

    /**
     * Finding an entity by its name.
     *
     * @param name - Entity name.
     * @return - Optional of found entity.
     */
    public abstract Optional<T> findByName(String name);
}
