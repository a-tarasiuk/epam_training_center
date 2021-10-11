package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * Tag DAO layer class.
 * Works with database.
 */
public abstract class TagDao extends EntityDao<Tag> {
    /**
     * Finding list of tag by gift certificated ID.
     *
     * @param id    - Gift certificate ID.
     * @return      - List of found tags.
     */
    public abstract List<Tag> findByGiftCertificateId(long id);
}
