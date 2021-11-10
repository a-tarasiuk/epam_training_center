package com.epam.esm.dao;

import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.pagination.EsmPagination;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entity abstract DAO layer class.<br>
 * Works with database.
 *
 * @param <T> Entity.
 */
public abstract class AbstractDao<T> {
    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected CriteriaBuilder cb;

    @Autowired
    protected EsmPagination esmPagination;

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
    public abstract void delete(T entity);

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

    /**
     * Find all entities with paginate.
     *
     * @param esmPagination Contain number of page and count elements on page.
     * @param entity        Entity.
     * @return Set of entities.
     */
    public Set<T> findAll(EsmPagination esmPagination, Class<T> entity) {
        int countPages = esmPagination.getPage();
        int elementsOnPage = esmPagination.getSize();

        checkEsmPaginationForValidityOrElseThrow(entity, countPages, elementsOnPage);

        CriteriaQuery<T> cq = cb.createQuery(entity);
        Root<T> root = cq.from(entity);
        cq.select(root);

        return em.createQuery(cq)
                .setFirstResult((countPages - NumberUtils.INTEGER_ONE) * elementsOnPage)
                .setMaxResults(elementsOnPage)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    protected void checkEsmPaginationForValidityOrElseThrow(Class<T> entity, int countPages, int elementsOnPage) {
        Long totalRows = findTotalRows(entity);
        Long maxPages = getMaxPages(elementsOnPage, totalRows);

        if (countPages > maxPages) {
            throw new IllegalArgumentException(MessagePropertyKey.EXCEPTION_ESM_PAGINATION_PAGE_OUT_OF_RANGE);
        }
    }

    private Long findTotalRows(Class<T> entity) {
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(entity)));

        return em.createQuery(cq).getSingleResult();
    }

    private Long getMaxPages(int elementsOnPage, Long totalRows) {
        return (totalRows + elementsOnPage - NumberUtils.INTEGER_ONE) / elementsOnPage;
    }
}
