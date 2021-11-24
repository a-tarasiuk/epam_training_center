package com.epam.esm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Esm extension of {@link org.springframework.data.repository.Repository} to provide additional methods to retrieve entities using the pagination and
 * sorting abstraction.
 *
 * @see Sort
 * @see Pageable
 * @see Page
 */
@NoRepositoryBean
public interface PagingAndSortingRepository<T, ID> extends EsmRepository<T, ID> {

    /**
     * Returns all entities sorted by the given options.
     *
     * @param sort sort option for queries.
     * @return all entities sorted by the given options
     */
    Iterable<T> findAll(Sort sort);

    /**
     * Returns a {@link org.springframework.data.domain.Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable pagination information.
     * @return a page of entities
     */
    Page<T> findAll(Pageable pageable);
}