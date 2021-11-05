package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Gift certificate to tag relation DAO layer class.
 * Works with database.
 */
public interface GiftCertificateToTagRelationDao<T> {
    GiftCertificateToTagRelation create(GiftCertificateToTagRelation relation);

    GiftCertificateToTagRelation create(GiftCertificate gc, Tag tag);

    void delete(GiftCertificateToTagRelation relation);

    Optional<GiftCertificateToTagRelation> find(GiftCertificate gc, Tag tag);

    boolean isExist(GiftCertificateToTagRelation relation);
    Optional<GiftCertificateToTagRelation> find(GiftCertificateToTagRelation relation);

    boolean isExist(GiftCertificate gc, Tag tag);

    void delete(GiftCertificate gc, Tag tag);

    List<GiftCertificateToTagRelation> findAll(GiftCertificate giftCertificate);
}
