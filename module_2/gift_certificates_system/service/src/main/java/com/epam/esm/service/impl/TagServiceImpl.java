package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistsException;
import com.epam.esm.exception.EntityInvalidException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.AbstractService;
import com.epam.esm.util.TagValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.PropertyKey.MESSAGE_ENTITY_EXISTS_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_ENTITY_INVALID_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_ENTITY_NOT_FOUND_EXCEPTION;

/**
 * Tag service implementation.
 */
@Log4j2
@Service
public class TagServiceImpl implements AbstractService<Tag> {
    private final TagDaoImpl tagDao;

    /**
     * Instantiates a new tag service.
     *
     * @param tagDao - Tag DAO layer.
     */
    @Autowired
    public TagServiceImpl(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag create(Tag entity) {
        if (!TagValidator.isValid(entity)) {
            throw new EntityInvalidException(MESSAGE_ENTITY_INVALID_EXCEPTION);
        }

        String tagName = entity.getName();
        Optional<Tag> optionalTag = tagDao.findByName(tagName);

        if (optionalTag.isPresent()) {
            throw new EntityExistsException(MESSAGE_ENTITY_EXISTS_EXCEPTION);
        }

        return tagDao.create(entity);
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Tag findById(long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        return optionalTag.orElseThrow(() -> new EntityNotFoundException(MESSAGE_ENTITY_NOT_FOUND_EXCEPTION));
    }

    @Override
    public boolean delete(long id) {
        boolean result;
        Optional<Tag> optionalTag = tagDao.findById(id);
        optionalTag.orElseThrow(() -> new EntityNotFoundException(MESSAGE_ENTITY_NOT_FOUND_EXCEPTION));
        result = tagDao.delete(id);
        return result;
    }
}
