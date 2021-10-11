package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * Gift certificate to tag relation DAO layer class.
 * Works with database.
 */
public abstract class GiftCertificateToTagRelationDao<T> {
    /**
     * Creating relation between gift certificate ID and tag ID.
     *
     * @param giftCertificateId     - Gift certificate ID.
     * @param tagId                 - Tag ID.
     * @return                      - Operation result (created or not created)
     */
    public abstract boolean create(long giftCertificateId, long tagId);

    /**
     * Deleting all tags by gift certificate ID.
     *
     * @param giftCertificateId     - Gift certificate ID.
     * @return                      - Operation result (deleted or not deleted)
     */
    public abstract boolean deleteAllTagsByGiftCertificateId(long giftCertificateId);
}
