package com.epam.esm.service;

/**
 * Abstract for SERVICE layer.
 * Work with DAO layer.
 *
 * @param <T> - Type of entity.
 */
public interface CreateService<T> extends AbstractService<T> {
    /**
     * Entity creation.
     *
     * @param entity - Entity.
     * @return - Created entity from the database.
     */
    T create(T entity);
}