package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Set;

/**
 * Tag DAO layer class.
 * Works with database.
 */
public interface TagDao extends EntityDao<Tag> {
    /**
     * Finding list of tag by gift certificated ID.
     *
     * @param id - Gift certificate ID.
     * @return - Set of found tags.
     */
    Set<Tag> findByGiftCertificateId(long id);

    /**
     * Deleting all tags by gift certificate ID.
     *
     * @param giftCertificateId - Gift certificate ID.
     * @return - Operation result (deleted or not deleted)
     */
    boolean deleteAllTagsByGiftCertificateId(long giftCertificateId);
}
