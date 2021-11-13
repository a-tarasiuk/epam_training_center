package com.epam.esm.service;

import com.epam.esm.entity.MostWidelyUsedTag;
import com.epam.esm.util.EsmPagination;

import java.util.Set;

/**
 * Abstract for SERVICE layer.
 * Work with DAO layer.
 *
 * @param <T> - Type of entity.
 */
public interface AbstractService<T> {
    /**
     * Entity creation.
     *
     * @param entity - Entity.
     * @return - Created entity from the database.
     */
    T create(T entity);

    /**
     * Search for all entities.
     *
     * @return - Set of found entities.
     */
    Set<T> findAll(EsmPagination esmPagination);

    /**
     * Finding entity by it is ID.
     *
     * @param id - Entity ID.
     * @return - Found entity from the database.
     */
    T findById(long id);

    /**
     * Deleting entity by it is ID.
     *
     * @param id - Entity ID.
     */
    void delete(long id);

    /**
     * Finds the most used tags with the user with the highest value of all orders.
     *
     * @return set of MostWidelyUsedTag.
     * @see com.epam.esm.entity.MostWidelyUsedTag
     */
    Set<MostWidelyUsedTag> findMostWidelyUsedTags();
}