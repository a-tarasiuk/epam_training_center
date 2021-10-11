package com.epam.esm.service;

import java.util.List;

/**
 * Abstract for SERVICE layer.
 * Work with DAO layer.
 *
 * @param <T>           - Type of entity.
 */
public abstract class AbstractService<T> {
    /**
     * Entity creation.
     *
     * @param entity    - Entity.
     * @return          - Created entity from the database.
     */
    public abstract T create(T entity);

    /**
     * Search for all entities.
     *
     * @return          - List of found entities.
     */
    public abstract List<T> findAll();

    /**
     * Finding entity by it is ID.
     *
     * @param id        - Entity ID.
     * @return          - Found entity from the database.
     */
    public abstract T findById(long id);

    /**
     * Deleting entity by it is ID.
     *
     * @param id        - Entity ID.
     * @return          - Operation result (true - if deleted, false - if not deleted)
     */
    public abstract boolean delete(long id);
}
