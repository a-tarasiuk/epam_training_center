package com.epam.esm.repository.dao;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.dao.AbstractDao;

import java.util.Set;

/**
 * Tag DAO layer class.
 * Works with database.
 */
public abstract class TagDao extends AbstractDao<Tag> {
    /**
     * Find all tags by gift certificate.
     *
     * @param certificate Gift certificate entity.
     * @return Set of found tags.
     */
    public abstract Set<Tag> findAllBy(GiftCertificate certificate);
}
