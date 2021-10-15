package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * Entity DAO layer class.
 * Works with database.
 */
public interface EntityDao<T> {
    /**
     * Create an entity in the database.
     *
     * @param entity - Entity being created.
     * @return - Created entity.
     */
    T create(T entity);

    /**
     * Delete an entity in the database.
     *
     * @param id - Entity ID.
     * @return - Operation result (entity deleted or not deleted)
     */
    boolean delete(long id);

    /**
     * Search for all entities.
     *
     * @return - List of found entities.
     */
    List<T> findAll();

    /**
     * Finding an entity by its ID.
     *
     * @param id - Entity ID.
     * @return - Optional of found entity.
     */
    Optional<T> findById(long id);

    /**
     * Finding an entity by its name.
     *
     * @param name - Entity name.
     * @return - Optional of found entity.
     */
    Optional<T> findByName(String name);
}
