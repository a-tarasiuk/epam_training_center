package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateToTagRelationDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.EntityFieldName;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Gift certificate to tag relation DAO implementation.
 */
@Log4j2
@Repository
public class GiftCertificateToTagRelationDaoImpl implements GiftCertificateToTagRelationDao<GiftCertificateToTagRelation> {
    private static final String FIND_ALL_BY_GIFT_CERTIFICATE_ID_SQL
            = "SELECT relation FROM GiftCertificateToTagRelation AS relation WHERE relation.giftCertificate = :giftCertificate";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GiftCertificateToTagRelation create(GiftCertificateToTagRelation relation) {
        entityManager.persist(relation);
        return relation;
    }

    @Override
    public GiftCertificateToTagRelation create(GiftCertificate gc, Tag tag) {
        GiftCertificateToTagRelation relation = createRelation(gc, tag);
        return create(relation);
    }

    @Override
    public void delete(GiftCertificateToTagRelation relation) {
        entityManager.remove(entityManager.contains(relation) ? relation : entityManager.merge(relation));
    }

    @Override
    public void delete(GiftCertificate gc, Tag tag) {
        GiftCertificateToTagRelation relation = createRelation(gc, tag);
        delete(relation);
    }

    @Override
    public List<GiftCertificateToTagRelation> findAll(GiftCertificate gc) {
        return entityManager.createQuery(FIND_ALL_BY_GIFT_CERTIFICATE_ID_SQL, GiftCertificateToTagRelation.class)
                .setParameter(EntityFieldName.GIFT_CERTIFICATE, gc)
                .getResultList();
    }

    @Override
    public Optional<GiftCertificateToTagRelation> find(GiftCertificateToTagRelation relation) {
        GiftCertificateToTagRelation foundRelation = entityManager.find(GiftCertificateToTagRelation.class, relation);
        return Optional.ofNullable(foundRelation);
    }

    @Override
    public Optional<GiftCertificateToTagRelation> find(GiftCertificate gc, Tag tag) {
        GiftCertificateToTagRelation relation = createRelation(gc, tag);
        return find(relation);
    }

    @Override
    public boolean isExist(GiftCertificateToTagRelation relation) {
        return find(relation).isPresent();
    }

    @Override
    public boolean isExist(GiftCertificate gc, Tag tag) {
        GiftCertificateToTagRelation relation = createRelation(gc, tag);
        return find(relation).isPresent();
    }

    private GiftCertificateToTagRelation createRelation(GiftCertificate gc, Tag tag) {
        return new GiftCertificateToTagRelation(gc, tag);
    }
}