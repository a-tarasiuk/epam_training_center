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
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.util.MessagePropertyKey.*;

public abstract class FindAllDao<T> {
    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected CriteriaBuilder cb;

    /**
     * Find all entities with paginate.
     *
     * @param pagination Contain number of page and count elements on page.
     * @param entity        Entity.
     * @return Set of entities.
     */
    public Set<T> findAll(EsmPagination pagination, Class<T> entity) {
        validatePaginationOrElseThrow(pagination, entity);

        CriteriaQuery<T> cq = cb.createQuery(entity);
        Root<T> root = cq.from(entity);
        cq.select(root);

        return executeQuery(cq, pagination);
    }

    protected void validatePaginationOrElseThrow(EsmPagination pagination, Class<T> entity) {
        int countPages = pagination.getPage();
        int elementsOnPage = pagination.getSize();

        Long totalRows = findTotalRows(entity);
        Long maxPages = getMaxPages(elementsOnPage, totalRows);

        if (countPages > maxPages) {
            throw new IllegalArgumentException(EXCEPTION_ESM_PAGINATION_PAGE_OUT_OF_RANGE);
        }
    }

    protected Set<T> executeQuery(CriteriaQuery<T> cq, EsmPagination pagination) {
        int countPages = pagination.getPage();
        int elementsOnPage = pagination.getSize();

        return em.createQuery(cq)
                .setFirstResult((countPages - NumberUtils.INTEGER_ONE) * elementsOnPage)
                .setMaxResults(elementsOnPage)
                .getResultStream()
                .collect(Collectors.toSet());
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
