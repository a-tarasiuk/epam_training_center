package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
     */
    void delete(T entity);

    /**
     * Search for all entities.
     *
     * @return - Set of found entities.
     */
    Set<T> findAll();

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
