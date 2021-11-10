package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.util.CriteriaQueryGenerator;
import com.epam.esm.util.DatabaseColumnName;
import com.epam.esm.util.EntityFieldName;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.ParameterName;
import com.epam.esm.util.SqlGenerator;
import com.epam.esm.util.pagination.EsmPagination;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
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
public class GiftCertificateDaoImpl extends GiftCertificateDao {
    private static final String FIND_GIFT_CERTIFICATES_BY_TAG_SQL
            = "SELECT gc FROM GiftCertificate AS gc INNER JOIN GiftCertificateToTagRelation AS relation ON relation.giftCertificate = gc WHERE relation.tag = :tag";
    private static final String FIND_GIFT_CERTIFICATE_BY_TAGS_SQL
            = "SELECT gc FROM GiftCertificate gc JOIN GiftCertificateToTagRelation relation on gc = relation.giftCertificate WHERE relation.tag IN (:tags) GROUP BY gc HAVING COUNT(gc) = :countTags";
    private static final String FIND_GIFT_CERTIFICATES_BY_USER_SQL
            = "SELECT gc FROM GiftCertificate gc JOIN Order o ON gc = o.giftCertificate WHERE o.user = :user";

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        em.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        em.remove(giftCertificate);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return em.merge(giftCertificate);
    }

    @Override
    public Set<GiftCertificate> findAll(EsmPagination esmPagination, Set<String> sortBy) {
        CriteriaQueryGenerator<GiftCertificate> cqg = new CriteriaQueryGenerator<>(em, GiftCertificate.class);
        CriteriaQuery<GiftCertificate> cq = cqg.generate(sortBy);
        return executeCriteriaQuery(cq, esmPagination);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        GiftCertificate giftCertificate = em.find(GiftCertificate.class, id);
        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        Optional<GiftCertificate> optionalGc;

        try {
            CriteriaQuery<GiftCertificate> cq = cb.createQuery(GiftCertificate.class);
            Root<GiftCertificate> fromGc = cq.from(GiftCertificate.class);
            Predicate condition = cb.equal(fromGc.get(ParameterName.NAME), name);
            cq.select(fromGc).where(condition);

            GiftCertificate gc = em.createQuery(cq)
                    .getSingleResult();

            optionalGc = Optional.of(gc);
        } catch (NoResultException e) {
            optionalGc = Optional.empty();
        }

        return optionalGc;
    }

    @Override
    public Set<GiftCertificate> findBy(String keyword) {
        //Get criteria builder
        CriteriaBuilder cb = em.getCriteriaBuilder();
        //Create the CriteriaQuery for Gift certificate object
        CriteriaQuery<GiftCertificate> cq = cb.createQuery(GiftCertificate.class);

        Root<GiftCertificate> root = cq.from(GiftCertificate.class);
        Path<String> namePath = root.get(DatabaseColumnName.NAME);
        Path<String> descriptionPath = root.get(DatabaseColumnName.DESCRIPTION);

        String keywordLike = SqlGenerator.like(keyword);

        Predicate nameLike = cb.like(namePath, keywordLike);
        Predicate descriptionLike = cb.like(descriptionPath, keywordLike);
        Predicate or = cb.or(nameLike, descriptionLike);

        cq.where(or);
        return executeCriteriaQuery(cq);
    }

    @Override
    public Set<GiftCertificate> findBy(Tag tag) {
        return em.createQuery(FIND_GIFT_CERTIFICATES_BY_TAG_SQL, GiftCertificate.class)
                .setParameter(EntityFieldName.TAG, tag)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GiftCertificate> findBy(Set<Tag> tags) {
        return em.createQuery(FIND_GIFT_CERTIFICATE_BY_TAGS_SQL, GiftCertificate.class)
                .setParameter(ParameterName.TAGS, tags)
                .setParameter(ParameterName.COUNT_TAGS, (long) tags.size())
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GiftCertificate> findBy(User user) {
        return em.createQuery(FIND_GIFT_CERTIFICATES_BY_USER_SQL, GiftCertificate.class)
                .setParameter(EntityFieldName.USER, user)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    private <T> Set<T> executeCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
        return em.createQuery(criteriaQuery)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    private <T> Set<T> executeCriteriaQuery(CriteriaQuery<T> criteriaQuery, EsmPagination esmPagination) {
        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);

        int elementsOnPage = esmPagination.getSize();
        int numberOfPage = esmPagination.getPage();

        int maxElements = typedQuery.getMaxResults();
        int maxPages = maxElements % elementsOnPage;

        int firstResult = numberOfPage * elementsOnPage;

        if (firstResult > maxPages) {
            throw new IllegalArgumentException(MessagePropertyKey.EXCEPTION_ESM_PAGINATION_PAGE_OUT_OF_RANGE);
        }

        return typedQuery
                .setFirstResult(firstResult)
                .setMaxResults(elementsOnPage)
                .getResultStream()
                .collect(Collectors.toSet());
    }
}
