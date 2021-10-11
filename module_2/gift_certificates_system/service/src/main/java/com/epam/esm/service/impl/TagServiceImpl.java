package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.TagService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

/**
 * Tag service implementation.
 */
@Log4j2
@Component
public class TagServiceImpl extends TagService {

    /**
     * Instantiates a new tag service.
     *
     * @param tagDao    - Tag DAO layer.
     */
    @Autowired
    public TagServiceImpl(TagDaoImpl tagDao) {
        super(tagDao);
    }

    @Override
    public Tag create(Tag entity) {
        String tagName = entity.getName();
        Optional<Tag> optionalTag = tagDao.findByName(tagName);

        if(optionalTag.isPresent()) {
            log.debug("Tag with name '{}' already exists in the database.", entity.getName());
            throw new EntityExistsException("Tag with name '" + entity.getName() + "' already exists in the database");
        }

        return optionalTag.orElse(tagDao.create(entity));
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Tag findById(long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        return optionalTag.orElseThrow(() -> new EntityNotFoundException("Resource not found for tag with id: " + id));
    }

    @Override
    public boolean delete(long id) {
        boolean result = false;
        Optional<Tag> optionalTag = tagDao.findById(id);

        if(optionalTag.isPresent()) {
            result = tagDao.delete(id);
            log.info(result ? "Tag with id '{}' deleted from the database." : "Tag with id '{}' not deleted from the database.", id);
        }

        return result;
    }
}
