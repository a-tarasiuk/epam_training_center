package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.Set;

/**
 * Tag DAO layer class.
 * Works with database.
 */
public abstract class TagDao extends AbstractDao<Tag> {
    /**
     * Find all tags by gift certificate.
     *
     * @param gc Gift certificate entity.
     * @return Set of found tags.
     */
    public abstract Set<Tag> findAllBy(GiftCertificate gc);
}
