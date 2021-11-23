package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateToTagRelationDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.ParameterName;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Gift certificate to tag relation DAO implementation.
 */
@Log4j2
@Repository
public class GiftCertificateToTagRelationDaoImpl implements GiftCertificateToTagRelationDao<GiftCertificateToTagRelation> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GiftCertificateToTagRelation create(GiftCertificateToTagRelation relation) {
        entityManager.persist(relation);
        return relation;
    }

    @Override
    public void delete(GiftCertificateToTagRelation relation) {
        entityManager.remove(entityManager.contains(relation) ? relation : entityManager.merge(relation));
    }

    /**
     * Full query:<br>
     * SELECT relation FROM GiftCertificateToTagRelation AS relation WHERE relation.giftCertificate = :giftCertificate
     */
    @Override
    public List<GiftCertificateToTagRelation> findAllBy(GiftCertificate certificate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateToTagRelation> cq = cb.createQuery(GiftCertificateToTagRelation.class);
        Root<GiftCertificateToTagRelation> from = cq.from(GiftCertificateToTagRelation.class);
        Predicate condition = cb.equal(from.get(ParameterName.GIFT_CERTIFICATE), certificate);
        cq.select(from).where(condition);

        return entityManager.createQuery(cq)
                .getResultList();
    }

    /**
     * Full query:<br>
     * SELECT relation FROM GiftCertificateToTagRelation AS relation WHERE relation.tag = :tag
     */
    @Override
    public List<GiftCertificateToTagRelation> findAllBy(Tag tag) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateToTagRelation> cq = cb.createQuery(GiftCertificateToTagRelation.class);
        Root<GiftCertificateToTagRelation> from = cq.from(GiftCertificateToTagRelation.class);
        Predicate condition = cb.equal(from.get(ParameterName.TAG), tag);
        cq.select(from).where(condition);

        return entityManager.createQuery(cq)
                .getResultList();
    }

    @Override
    public Optional<GiftCertificateToTagRelation> findBy(GiftCertificateToTagRelation relation) {
        GiftCertificateToTagRelation foundRelation = entityManager.find(GiftCertificateToTagRelation.class, relation);
        return Optional.ofNullable(foundRelation);
    }

    @Override
    public boolean isExist(GiftCertificate gc, Tag tag) {
        GiftCertificateToTagRelation relation = createRelation(gc, tag);
        return findBy(relation).isPresent();
    }

    private GiftCertificateToTagRelation createRelation(GiftCertificate gc, Tag tag) {
        return new GiftCertificateToTagRelation(gc, tag);
    }
}