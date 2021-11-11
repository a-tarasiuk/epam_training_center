package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.util.CriteriaQueryGenerator;
import com.epam.esm.util.DatabaseColumnName;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.ParameterName;
import com.epam.esm.util.SqlGenerator;
import com.epam.esm.util.pagination.EsmPagination;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final GiftCertificateToTagRelationDaoImpl relationDao;

    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateToTagRelationDaoImpl relationDao) {
        this.relationDao = relationDao;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        em.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return em.merge(giftCertificate);
    }

    @Override
    public Set<GiftCertificate> findAll(EsmPagination pagination, Set<String> sortBy) {
        CriteriaQueryGenerator<GiftCertificate> cqg = new CriteriaQueryGenerator<>(em, GiftCertificate.class);
        CriteriaQuery<GiftCertificate> cq = cqg.generate(sortBy);
        validatePaginationOrElseThrow(pagination, GiftCertificate.class);
        return executeQuery(cq, pagination);
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
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
        return relationDao.findAllBy(tag).stream()
                .map(GiftCertificateToTagRelation::getGiftCertificate)
                .collect(Collectors.toSet());
    }

    /**
     * Full JPA query:<br>
     * SELECT gc FROM GiftCertificate gc JOIN GiftCertificateToTagRelation relation on gc = relation.giftCertificate WHERE relation.tag IN (:tags) GROUP BY gc HAVING COUNT(gc) = :countTags
     */
    @Override
    public Optional<GiftCertificate> findBy(Set<Tag> tags) {
        Optional<GiftCertificate> optional;

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GiftCertificate> cq = cb.createQuery(GiftCertificate.class);
            Root<GiftCertificateToTagRelation> from = cq.from(GiftCertificateToTagRelation.class);
            Path<GiftCertificate> choose = from.get(ParameterName.GIFT_CERTIFICATE);
            cq.select(choose).where(from.get(ParameterName.TAG).in(tags)).groupBy(choose).having(cb.count(choose).in(tags.size()));

            GiftCertificate gc = em.createQuery(cq).getSingleResult();
            optional = Optional.of(gc);
        } catch (NoResultException e) {
            optional = Optional.empty();
        }

        return optional;
    }

    /**
     * Full JPA query:<br>
     * SELECT gc FROM GiftCertificate gc JOIN Order o ON gc = o.giftCertificate WHERE o.user = :user
     */
    @Override
    public Set<GiftCertificate> findBy(User user) {
        CriteriaQuery<GiftCertificate> cq = cb.createQuery(GiftCertificate.class);
        Root<Order> from = cq.from(Order.class);
        Predicate condition = cb.equal(from.get(ParameterName.USER), user);
        cq.select(from.get(ParameterName.GIFT_CERTIFICATE)).where(condition);

        return em.createQuery(cq)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    private <T> Set<T> executeCriteriaQuery(CriteriaQuery<T> criteriaQuery) {
        return em.createQuery(criteriaQuery)
                .getResultStream()
                .collect(Collectors.toSet());
    }
}
