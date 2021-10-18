package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.GiftCertificateToTagRelationDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistsException;
import com.epam.esm.exception.EntityInvalidException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.FieldInvalidException;
import com.epam.esm.service.GitCertificateService;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.GiftCertificateUpdater;
import com.epam.esm.util.GiftCertificateValidator;
import com.epam.esm.util.SqlSortOperator;
import com.epam.esm.util.TagValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.util.PropertyKey.MESSAGE_DESCRIPTION_INVALID_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_ENTITY_EXISTS_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_ENTITY_INVALID_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_ENTITY_NOT_FOUND_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_KEYWORD_INVALID_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_NAME_INVALID_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_REQUIRED_FIELDS_EMPTY_EXCEPTION;

/**
 * Gift certificate service implementation.
 */
@Log4j2
@Service
public class GiftCertificateServiceImpl implements GitCertificateService {
    private final TagDaoImpl tagDao;
    private final GiftCertificateDaoImpl giftCertificateDao;
    private final GiftCertificateToTagRelationDaoImpl relationDao;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param giftCertificateDao - Gift certificate DAO layer.
     * @param tagDao             - Tag DAO layer.
     * @param relationDao        - Gift certificate to tag relation DAO layer.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDaoImpl giftCertificateDao, TagDaoImpl tagDao, GiftCertificateToTagRelationDaoImpl relationDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.relationDao = relationDao;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public GiftCertificate create(GiftCertificate entity) {
        List<Tag> tags = entity.getTags();
        if (!GiftCertificateValidator.isValid(entity) && !TagValidator.isValid(tags)) {
            throw new EntityInvalidException(MESSAGE_ENTITY_INVALID_EXCEPTION);
        }

        String name = entity.getName();
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findByName(name);
        if (optionalGiftCertificate.isPresent()) {
            throw new EntityExistsException(MESSAGE_ENTITY_EXISTS_EXCEPTION);
        }

        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        entity.setCreateDate(currentLocalDateTime);
        entity.setLastUpdateDate(currentLocalDateTime);

        GiftCertificate createdGiftCertificate = giftCertificateDao.create(entity);
        createRelations(createdGiftCertificate);

        return createdGiftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        findAndSetTags(giftCertificates);

        log.info("Found '{}' gift certificates: {}", giftCertificates.size(), giftCertificates);
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findAllSorted(Set<ColumnName> columnNames) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAllSorted(columnNames);
        findAndSetTags(giftCertificates);

        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findAllSortedWithType(Set<ColumnName> columnNames, SqlSortOperator sqlSortOperator) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAllSorted(columnNames, sqlSortOperator);
        findAndSetTags(giftCertificates);

        return giftCertificates;
    }

    @Override
    public GiftCertificate findById(long id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(() -> new EntityNotFoundException(MESSAGE_ENTITY_NOT_FOUND_EXCEPTION));
        List<Tag> tagList = tagDao.findByGiftCertificateId(id);
        giftCertificate.setTags(tagList);

        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName) {
        Optional<Tag> optionalTag = tagDao.findByName(tagName);
        Tag tag = optionalTag.orElseThrow(() -> new EntityNotFoundException(MESSAGE_ENTITY_NOT_FOUND_EXCEPTION));
        long tagId = tag.getId();

        List<GiftCertificate> giftCertificates = giftCertificateDao.findByTagId(tagId);
        if (!ObjectUtils.isEmpty(giftCertificates)) {
            findAndSetTags(giftCertificates);
        } else {
            throw new EntityNotFoundException(MESSAGE_ENTITY_NOT_FOUND_EXCEPTION);
        }

        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByPartOfName(String partOfName) {
        if (!ObjectUtils.isEmpty(partOfName.trim())) {
            List<GiftCertificate> giftCertificates = giftCertificateDao.findByPartOfName(partOfName);

            if (giftCertificates != null && !giftCertificates.isEmpty()) {
                findAndSetTags(giftCertificates);
            } else {
                throw new EntityNotFoundException(MESSAGE_ENTITY_NOT_FOUND_EXCEPTION);
            }

            return giftCertificates;
        } else {
            throw new EntityInvalidException(MESSAGE_NAME_INVALID_EXCEPTION);
        }
    }

    @Override
    public List<GiftCertificate> findByPartOfDescription(String partOfDescription) {
        if (!ObjectUtils.isEmpty(partOfDescription.trim())) {
            List<GiftCertificate> giftCertificates = giftCertificateDao.findByPartOfDescription(partOfDescription);

            if (giftCertificates != null && !giftCertificates.isEmpty()) {
                findAndSetTags(giftCertificates);
            } else {
                throw new EntityNotFoundException(MESSAGE_ENTITY_NOT_FOUND_EXCEPTION);
            }

            return giftCertificates;
        } else {
            throw new EntityInvalidException(MESSAGE_DESCRIPTION_INVALID_EXCEPTION);
        }
    }

    @Override
    public List<GiftCertificate> findByKeyword(String keyword) {
        if (!ObjectUtils.isEmpty(keyword.trim())) {
            List<GiftCertificate> giftCertificates = giftCertificateDao.findByKeyword(keyword);

            if (giftCertificates != null && !giftCertificates.isEmpty()) {
                findAndSetTags(giftCertificates);
            } else {
                throw new EntityNotFoundException(MESSAGE_ENTITY_NOT_FOUND_EXCEPTION);
            }

            return giftCertificates;
        } else {
            throw new EntityInvalidException(MESSAGE_KEYWORD_INVALID_EXCEPTION);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean update(long id, GiftCertificate giftCertificate) {
        if (!requiredFieldsAreEmpty(giftCertificate)) {
            boolean result;

            GiftCertificate foundGiftCertificate = giftCertificateDao.findById(id).orElseThrow(() -> new EntityNotFoundException(MESSAGE_ENTITY_NOT_FOUND_EXCEPTION));
            giftCertificate = GiftCertificateUpdater.update(foundGiftCertificate, giftCertificate);

            if (GiftCertificateValidator.isValid(giftCertificate)) {
                List<Tag> tags = giftCertificate.getTags();

                if (!ObjectUtils.isEmpty(tags)) {
                    if (tags.stream().allMatch(TagValidator::isValid)) {
                        deleteIrrelevantRelations(giftCertificate);
                        createRelations(giftCertificate);
                    } else {
                        throw new EntityInvalidException(MESSAGE_ENTITY_INVALID_EXCEPTION);
                    }
                }

                result = giftCertificateDao.update(id, giftCertificate);
            } else {
                throw new EntityInvalidException(MESSAGE_ENTITY_INVALID_EXCEPTION);
            }

            return result;
        } else {
            throw new FieldInvalidException(MESSAGE_REQUIRED_FIELDS_EMPTY_EXCEPTION);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void createRelations(GiftCertificate giftCertificate) {
        long giftCertificateId = giftCertificate.getId();

        giftCertificate.getTags().forEach(tag -> {
            if (TagValidator.isValid(tag)) {
                String tagName = tag.getName();
                Optional<Tag> optionalTag = tagDao.findByName(tagName);

                long tagId;
                if (optionalTag.isPresent()) {
                    Tag foundTag = optionalTag.get();
                    tagId = foundTag.getId();

                    Optional<GiftCertificateToTagRelation> relationOptional = relationDao.find(giftCertificateId, tagId);
                    if (!relationOptional.isPresent()) {
                        relationDao.create(giftCertificateId, tagId);
                    }
                } else {
                    Tag createdTag = tagDao.create(tag);
                    tagId = createdTag.getId();
                    relationDao.create(giftCertificateId, tagId);
                }

                tag.setId(tagId);
            } else {
                throw new EntityInvalidException(MESSAGE_ENTITY_INVALID_EXCEPTION);
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean delete(long id) {
        boolean result;
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);

        if (optionalGiftCertificate.isPresent()) {
            boolean deleteTagsResult = tagDao.deleteAllTagsByGiftCertificateId(id);
            boolean deleteRelationResult = giftCertificateDao.delete(id);
            result = deleteTagsResult == deleteRelationResult;
            log.info(result ? "Gift certificate with id '{}' deleted from the database."
                    : "Gift certificate with id '{}' not deleted from the database.", id);
        } else {
            throw new EntityNotFoundException(MESSAGE_ENTITY_NOT_FOUND_EXCEPTION);
        }

        return result;
    }

    private boolean requiredFieldsAreEmpty(GiftCertificate giftCertificate) {
        return ObjectUtils.isEmpty(giftCertificate.getName()) && ObjectUtils.isEmpty(giftCertificate.getDescription())
                && ObjectUtils.isEmpty(giftCertificate.getPrice()) && ObjectUtils.isEmpty(giftCertificate.getDuration());
    }

    private void deleteIrrelevantRelations(GiftCertificate giftCertificate) {
        long giftCertificateId = giftCertificate.getId();

        // Find all current tags
        List<String> currentTags = tagDao.findByGiftCertificateId(giftCertificateId).stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        log.info("Current tags: " + currentTags);

        // Get all tag from request
        List<String> requestTags = giftCertificate.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        log.info("Requested tags: " + requestTags);

        // Find different tags
        List<String> differenceTags = currentTags.stream()
                .filter(tag -> !requestTags.contains(tag))
                .collect(Collectors.toList());
        log.info("Difference tags: " + differenceTags);

        // Delete distinctive tag relations
        differenceTags.forEach(tagName -> {
            Optional<Tag> optionalTag = tagDao.findByName(tagName);

            if (optionalTag.isPresent()) {
                Tag tag = optionalTag.get();
                long tagId = tag.getId();

                relationDao.delete(giftCertificateId, tagId);
            }
        });
    }

    private void findAndSetTags(List<GiftCertificate> giftCertificates) {
        giftCertificates.forEach(giftCertificate -> {
            long id = giftCertificate.getId();
            List<Tag> tags = tagDao.findByGiftCertificateId(id);
            giftCertificate.setTags(tags);
        });
    }
}
