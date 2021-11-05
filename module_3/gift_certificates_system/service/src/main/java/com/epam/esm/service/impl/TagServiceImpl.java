package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.AbstractService;
import com.epam.esm.util.MessagePropertyKey;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Tag service implementation.
 */
@Service
@Transactional
public class TagServiceImpl implements AbstractService<TagDto> {
    private final ModelMapper modelMapper;
    private final TagDao tagDao;

    /**
     * Instantiates a new tag service.
     *
     * @param tagDao - Tag DAO layer.
     */
    @Autowired
    public TagServiceImpl(ModelMapper modelMapper, TagDao tagDao) {
        this.modelMapper = modelMapper;
        this.tagDao = tagDao;
    }

    @Override
    public TagDto create(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        checkIfTagExistsOrElseThrow(tag);
        Tag createdTag = tagDao.create(tag);
        return modelMapper.map(createdTag, TagDto.class);
    }

    @Override
    public Set<TagDto> findAll() {
        return tagDao.findAll().stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public TagDto findById(long id) {
        return tagDao.findById(id)
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new EntityNotFoundException(MessagePropertyKey.EXCEPTION_TAG_ID_NOT_FOUND));
    }

    @Override
    public void delete(long id) {
        Tag foundTag = tagDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessagePropertyKey.EXCEPTION_TAG_ID_NOT_FOUND));
        tagDao.delete(foundTag);
    }

    private void checkIfTagExistsOrElseThrow(Tag tag) {
        String tagName = tag.getName();
        tagDao.findByName(tagName).ifPresent(t -> {
            throw new EntityExistsException(MessagePropertyKey.EXCEPTION_TAG_NAME_EXISTS);
        });
    }
}
