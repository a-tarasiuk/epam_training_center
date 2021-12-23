package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificateToTagRelation;

import java.util.List;
import java.util.Optional;

/**
 * Gift certificate to tag relation DAO layer class.
 * Works with database.
 */
public interface GiftCertificateToTagRelationDao<T> {
    /**
     * Creating relation between gift certificate ID and tag ID.
     *
     * @param giftCertificateId - Gift certificate ID.
     * @param tagId             - Tag ID.
     * @return - Operation result (created or not created)
     */
    boolean create(long giftCertificateId, long tagId);

    /**
     * Delete relation between gift certificate ID and tag ID.
     *
     * @param giftCertificateId - Gift certificate ID.
     * @param tagId             - Tag ID.
     * @return - Operation result (deleted or not deleted)
     */
    boolean delete(long giftCertificateId, long tagId);

    /**
     * Check is exists relation between gift certificate ID and tag ID.
     *
     * @param giftCertificateId - Gift certificate ID.
     * @param tagId             - Tag ID.
     * @return - Optional of relation.
     */
    Optional<GiftCertificateToTagRelation> find(long giftCertificateId, long tagId);

    /**
     * Find all relations for gift certificate.
     *
     * @param id - Gift certificate ID.
     * @return - Operation result (deleted or not deleted)
     */
    List<GiftCertificateToTagRelation> findAllByGiftCertificateId(long id);
}
