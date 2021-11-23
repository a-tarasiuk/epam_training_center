package com.epam.esm.service;

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
     * Search for all entities.
     *
     * @return - Set of found entities.
     */
    Set<T> findAll(EsmPagination pagination);

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
}