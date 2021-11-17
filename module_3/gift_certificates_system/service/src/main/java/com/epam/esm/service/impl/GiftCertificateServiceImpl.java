package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateToTagRelationDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateUpdateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistingException;
import com.epam.esm.exception.EntityNonExistentException;
import com.epam.esm.service.GitCertificateService;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.GiftCertificateFieldChecker;
import com.epam.esm.util.GiftCertificateUpdater;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND;
import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_NAME_EXISTS;
import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_TAG_NAMES_NOT_FOUND;
import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_UPDATE_FIELDS_EMPTY;
import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_TAG_NAME_NOT_FOUND;

/**
 * Gift certificate service implementation.
 */
@Log4j2
@Service
@Transactional
public class GiftCertificateServiceImpl implements GitCertificateService {
    private final TagDao tagDao;
    private final GiftCertificateDao gcDao;
    private final GiftCertificateToTagRelationDaoImpl relationDao;
    private final ModelMapper modelMapper;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param gcDao       - Gift certificate DAO layer.
     * @param tagDao      - Tag DAO layer.
     * @param relationDao - Gift certificate to tag relation DAO layer.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao gcDao, TagDao tagDao, GiftCertificateToTagRelationDaoImpl relationDao, ModelMapper modelMapper) {
        this.gcDao = gcDao;
        this.tagDao = tagDao;
        this.relationDao = relationDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto gcCreateDto) {
        checkIfNonExistsOrElseThrow(gcCreateDto);

        GiftCertificate gc = modelMapper.map(gcCreateDto, GiftCertificate.class);
        GiftCertificate createdGc = gcDao.create(gc);

        gcCreateDto.getTags().stream()
                .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                .map(tag -> tagDao.findByName(tag.getName()).orElseGet(() -> tagDao.create(tag)))
                .map(tag -> new GiftCertificateToTagRelation(createdGc, tag))
                .forEach(relation -> {
                    if (!relationDao.findBy(relation).isPresent()) {
                        relationDao.create(relation);
                    }
                });

        GiftCertificateDto gcDto = modelMapper.map(createdGc, GiftCertificateDto.class);
        Set<TagDto> tagsDto = tagDao.findAllBy(createdGc).stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toSet());
        gcDto.setTags(tagsDto);

        return gcDto;
    }

    @Override
    public Set<GiftCertificateDto> findAll(EsmPagination esmPagination) {
        return gcDao.findAll(esmPagination, GiftCertificate.class).stream()
                .map(this::buildGiftCertificateDtoWithTags)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GiftCertificateDto> findAll(EsmPagination esmPagination, Set<String> sortBy) {
        return gcDao.findAll(esmPagination, sortBy).stream()
                .map(this::buildGiftCertificateDtoWithTags)
                .collect(Collectors.toSet());
    }

    @Override
    public GiftCertificateDto findById(long id) {
        GiftCertificate gc = gcDao.findById(id)
                .orElseThrow(() -> new EntityNonExistentException(EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, id));
        return buildGiftCertificateDtoWithTags(gc);
    }

    @Override
    public Set<GiftCertificateDto> findByTagNames(Set<String> names) {
        Set<Tag> tags = names.stream()
                .map(name -> tagDao.findByName(name)
                        .orElseThrow(() -> new EntityNonExistentException(EXCEPTION_TAG_NAME_NOT_FOUND, name)))
                .collect(Collectors.toSet());

        Set<GiftCertificate> giftCertificates = gcDao.findBy(tags);

        if (ObjectUtils.isNotEmpty(giftCertificates)) {
            return giftCertificates.stream()
                    .map(this::buildGiftCertificateDtoWithTags)
                    .collect(Collectors.toSet());
        } else {
            throw new EntityNonExistentException(EXCEPTION_GIFT_CERTIFICATE_TAG_NAMES_NOT_FOUND, String.join(", ", names));
        }
    }

    @Override
    public Set<GiftCertificateDto> findByKeyword(String keyword) {
        Set<GiftCertificate> gcs = gcDao.findBy(keyword);
        return buildGiftCertificatesDtoWithTags(gcs);
    }

    @Override
    public GiftCertificateDto update(long id, GiftCertificateUpdateDto gcUpdateDto) {
        GiftCertificate foundGc = gcDao.findById(id)
                .orElseThrow(() -> new EntityNonExistentException(EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, id));

        if (GiftCertificateFieldChecker.isFilledOneField(gcUpdateDto)) {
            GiftCertificate updatedGc = modelMapper.map(gcUpdateDto, GiftCertificate.class);

            GiftCertificate updated = GiftCertificateUpdater.update(foundGc, updatedGc);
            GiftCertificate created = gcDao.update(updated);

            if (ObjectUtils.isNotEmpty(gcUpdateDto.getTags())) {
                updateRelations(created, gcUpdateDto);
            }

            return buildGiftCertificateDtoWithTags(created);
        } else {
            throw new IllegalArgumentException(EXCEPTION_GIFT_CERTIFICATE_UPDATE_FIELDS_EMPTY);
        }
    }

    @Override
    public void delete(long id) {
        Optional<GiftCertificate> optionalGc = gcDao.findById(id);
        GiftCertificate gc = optionalGc.orElseThrow(() -> new EntityNonExistentException(EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, id));
        delete(gc);
    }

    private void checkIfNonExistsOrElseThrow(GiftCertificateDto giftCertificateDto) {
        String name = giftCertificateDto.getName();
        Optional<GiftCertificate> optionalGiftCertificate = gcDao.findByName(name);

        if (optionalGiftCertificate.isPresent()) {
            throw new EntityExistingException(EXCEPTION_GIFT_CERTIFICATE_NAME_EXISTS, name);
        }
    }

    private void updateRelations(GiftCertificate gc, GiftCertificateUpdateDto updateDto) {
        Set<Tag> tagsFromRequest = findTagsBy(updateDto);
        tagsFromRequest.forEach(tag -> createRelationIfNonExist(gc, tag));
        deleteIrrelevantRelations(gc, tagsFromRequest);
    }

    private void createRelationIfNonExist(GiftCertificate gc, Tag tag) {
        String tagName = tag.getName();
        Optional<Tag> optionalTag = tagDao.findByName(tagName);

        if (optionalTag.isPresent()) {
            Tag foundTag = optionalTag.get();
            boolean isExistRelation = relationDao.isExist(gc, foundTag);

            if (!isExistRelation) {
                GiftCertificateToTagRelation relation = new GiftCertificateToTagRelation(gc, foundTag);
                relationDao.create(relation);
            }
        } else {
            Tag createdTag = tagDao.create(tag);
            GiftCertificateToTagRelation relation = new GiftCertificateToTagRelation(gc, createdTag);
            relationDao.create(relation);
        }
    }

    private Set<Tag> findTagsBy(GiftCertificate gc) {
        return tagDao.findAllBy(gc);
    }

    private Set<TagDto> findTagsDtoBy(GiftCertificate gc) {
        return findTagsBy(gc).stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toSet());
    }

    private Set<Tag> findTagsBy(GiftCertificateUpdateDto gc) {
        return gc.getTags().stream()
                .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                .collect(Collectors.toSet());
    }

    private GiftCertificateDto buildGiftCertificateDtoWithTags(GiftCertificate gc) {
        Set<TagDto> tagsDto = findTagsDtoBy(gc);
        GiftCertificateDto gcDto = modelMapper.map(gc, GiftCertificateDto.class);
        gcDto.setTags(tagsDto);
        return gcDto;
    }

    private Set<GiftCertificateDto> buildGiftCertificatesDtoWithTags(Set<GiftCertificate> gcs) {
        return gcs.stream()
                .map(this::buildGiftCertificateDtoWithTags)
                .collect(Collectors.toSet());
    }

    private void delete(GiftCertificate gc) {
        deleteRelations(gc);
        gcDao.delete(gc);
    }

    private void deleteRelations(GiftCertificate gc) {
        relationDao.findAllBy(gc)
                .forEach(relationDao::delete);
    }

    private void deleteIrrelevantRelations(GiftCertificate gc, Set<Tag> tagsFromRequest) {
        Set<Tag> tagsFromDatabase = findTagsBy(gc);
        Set<Tag> tagForRemove = new HashSet<>(tagsFromDatabase);
        tagForRemove.removeAll(tagsFromRequest);
        tagForRemove.stream()
                .map(tag -> new GiftCertificateToTagRelation(gc, tag))
                .forEach(relationDao::delete);
    }
}
