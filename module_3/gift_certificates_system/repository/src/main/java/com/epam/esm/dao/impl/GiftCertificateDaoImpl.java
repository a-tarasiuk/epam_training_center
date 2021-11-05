package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.CriteriaQueryGenerator;
import com.epam.esm.util.DatabaseColumnName;
import com.epam.esm.util.DatabaseTableName;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.SqlQueryGenerator;
import com.epam.esm.util.pagination.EsmPagination;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Gift certificate DAO implementation.
 */
@Log4j2
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String FIND_ALL_GIFT_CERTIFICATES_SQL
            = "SELECT gc FROM GiftCertificate AS gc";
    private static final String FIND_GIFT_CERTIFICATE_BY_NAME_SQL
            = "SELECT gc FROM GiftCertificate AS gc WHERE gc.name = :name";
    private static final String FIND_GIFT_CERTIFICATES_BY_TAG_SQL
            = "SELECT gc FROM GiftCertificate AS gc INNER JOIN GiftCertificateToTagRelation AS relation ON relation.giftCertificate = gc WHERE relation.tag = :tag";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        entityManager.remove(giftCertificate);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public Set<GiftCertificate> findAll() {
        return entityManager.createQuery(FIND_ALL_GIFT_CERTIFICATES_SQL, GiftCertificate.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GiftCertificate> findAll(EsmPagination esmPagination, Set<String> sortBy) {
        CriteriaQueryGenerator<GiftCertificate> cqg = new CriteriaQueryGenerator<>(entityManager, GiftCertificate.class);
        CriteriaQuery<GiftCertificate> cq = cqg.generate(sortBy);
        return executeCriteriaQuery(cq, esmPagination);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        Optional<GiftCertificate> optionalGiftCertificate;

        try {
            optionalGiftCertificate = Optional.of(entityManager.createQuery(FIND_GIFT_CERTIFICATE_BY_NAME_SQL, GiftCertificate.class)
                    .setParameter(DatabaseColumnName.NAME, name)
                    .getSingleResult());
        } catch (NoResultException e) {
            optionalGiftCertificate = Optional.empty();
        }

        return optionalGiftCertificate;
    }

    @Override
    public Set<GiftCertificate> findByKeyword(String keyword) {
        //Get criteria builder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //Create the CriteriaQuery for Gift certificate object
        CriteriaQuery<GiftCertificate> cq = cb.createQuery(GiftCertificate.class);

        Root<GiftCertificate> root = cq.from(GiftCertificate.class);
        Path<String> namePath = root.get(DatabaseColumnName.NAME);
        Path<String> descriptionPath = root.get(DatabaseColumnName.DESCRIPTION);

        String keywordLike = SqlQueryGenerator.forLikeOperator(keyword);

        Predicate nameLike = cb.like(namePath, keywordLike);
        Predicate descriptionLike = cb.like(descriptionPath, keywordLike);
        Predicate or = cb.or(nameLike, descriptionLike);

        cq.where(or);
        return executeCriteriaQuery(cq);
    }

    @Override
    public Set<GiftCertificate> findByTag(Tag tag) {
        return entityManager.createQuery(FIND_GIFT_CERTIFICATES_BY_TAG_SQL, GiftCertificate.class)
                .setParameter(DatabaseTableName.TAG, tag)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    private <T> Set<T> executeCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    private <T> Set<T> executeCriteriaQuery(CriteriaQuery<T> criteriaQuery, EsmPagination esmPagination) {
        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);

        int elementsOnPage = esmPagination.getSize();
        int numberOfPage = esmPagination.getPage();

        int maxElements = typedQuery.getMaxResults();
        int maxPages = maxElements % elementsOnPage;

        int firstResult = numberOfPage * elementsOnPage;

        if(firstResult > maxPages) {
            throw new IllegalArgumentException(MessagePropertyKey.EXCEPTION_ESM_PAGINATION_PAGE_OUT_OF_RANGE);
        }

        return typedQuery
                .setFirstResult(firstResult)
                .setMaxResults(elementsOnPage)
                .getResultStream()
                .collect(Collectors.toSet());
    }
}
