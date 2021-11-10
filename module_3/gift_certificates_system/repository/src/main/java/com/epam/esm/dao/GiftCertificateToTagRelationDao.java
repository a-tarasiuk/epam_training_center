package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Gift certificate to tag relation DAO layer class.<br>
 * Works with database.
 */
public interface GiftCertificateToTagRelationDao<T> {
    GiftCertificateToTagRelation create(GiftCertificate gc, Tag tag);
    GiftCertificateToTagRelation create(GiftCertificateToTagRelation relation);

    void delete(GiftCertificate gc, Tag tag);
    void delete(GiftCertificateToTagRelation relation);

    boolean isExist(GiftCertificate gc, Tag tag);

    Optional<GiftCertificateToTagRelation> find(GiftCertificate gc, Tag tag);
    Optional<GiftCertificateToTagRelation> find(GiftCertificateToTagRelation relation);

    List<GiftCertificateToTagRelation> findAll(GiftCertificate giftCertificate);
}
