package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * Tag DAO layer class.
 * Works with database.
 */
public interface TagDao extends EntityDao<Tag> {
    /**
     * Finding list of tag by gift certificated ID.
     *
     * @param id - Gift certificate ID.
     * @return - List of found tags.
     */
    List<Tag> findByGiftCertificateId(long id);

    /**
     * Deleting all tags by gift certificate ID.
     *
     * @param giftCertificateId - Gift certificate ID.
     * @return - Operation result (deleted or not deleted)
     */
    boolean deleteAllTagsByGiftCertificateId(long giftCertificateId);
}
