package com.epam.esm.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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
    Set<T> findAll();

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
