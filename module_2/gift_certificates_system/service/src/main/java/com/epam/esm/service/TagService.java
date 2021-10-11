package com.epam.esm.service;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Tag SERVICE layer.
 */
@Component
public abstract class TagService extends AbstractService<Tag> {

    protected TagDaoImpl tagDao;

    /**
     * Instantiates a new tag DAO layer.
     */
    @Autowired
    public TagService(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }
}
