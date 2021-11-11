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
    /**
     * Create gift certificate to tag relation by gift certificate and tag.
     *
     * @param gc  Gift certificate.
     * @param tag Tag.
     * @return Created gift certificate to tag relation entity.
     */
    GiftCertificateToTagRelation create(GiftCertificate gc, Tag tag);

    /**
     * Create gift certificate to tag relation.
     *
     * @param relation Gift certificate to tag relation entity.
     * @return Created gift certificate to tag relation entity.
     */
    GiftCertificateToTagRelation create(GiftCertificateToTagRelation relation);

    /**
     * Delete gift certificate to tag relation by gift certificate and tag.
     *
     * @param gc  Gift certificate entity.
     * @param tag Tag entity.
     */
    void delete(GiftCertificate gc, Tag tag);

    /**
     * Delete gift certificate to tag relation entity.
     *
     * @param relation Gift certificate entity.
     */
    void delete(GiftCertificateToTagRelation relation);

    /**
     * Check is exist gift certificate to tag relation.
     *
     * @param gc  Gift certificate entity.
     * @param tag Tag entity.
     * @return Boolean (true - is found relation or else otherwise false)
     */
    boolean isExist(GiftCertificate gc, Tag tag);

    /**
     * Find gift certificate to tag relation by gift certificate entity and tag entity.
     *
     * @param gc  Gift certificate entity.
     * @param tag Tag entity.
     * @return Optional of gift certificate to tag relation.
     */
    Optional<GiftCertificateToTagRelation> findBy(GiftCertificate gc, Tag tag);

    /**
     * Find gift certificate to tag relation by gift certificate to tag relation entity.
     *
     * @param relation Gift certificate to tag relation entity.
     * @return Optional of gift certificate to tag relation.
     */
    Optional<GiftCertificateToTagRelation> findBy(GiftCertificateToTagRelation relation);

    /**
     * Find all gift certificate to tag relation by tag entity.
     *
     * @param tag Tag entity.
     * @return List of gift certificate to tag relation.
     */
    List<GiftCertificateToTagRelation> findAllBy(Tag tag);


    /**
     * Find all gift certificate to tag relation by gift certificate entity.
     *
     * @param giftCertificate Gift certificate entity.
     * @return List of gift certificate to tag relation.
     */
    List<GiftCertificateToTagRelation> findAllBy(GiftCertificate giftCertificate);
}
