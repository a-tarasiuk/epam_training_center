package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * Entity DAO layer class.
 * Works with database.
 */
public abstract class EntityDao<T> {
    /**
     * Create an entity in the database.
     *
     * @param entity    - Entity being created.
     * @return          - Created entity.
     */
    public abstract T create(T entity);

    /**
     * Delete an entity in the database.
     *
     * @param id        - Entity ID.
     * @return          - Operation result (entity deleted or not deleted)
     */
    public abstract boolean delete(long id);

    /**
     * Search for all entities.
     *
     * @return          - List of found entities.
     */
    public abstract List<T> findAll();

    /**
     * Finding an entity by its ID.
     *
     * @param id        - Entity ID.
     * @return          - Optional of found entity.
     */
    public abstract Optional<T> findById(long id);

    /**
     * Finding an entity by its name.
     *
     * @param name      - Entity name.
     * @return          - Optional of found entity.
     */
    public abstract Optional<T> findByName(String name);
}
