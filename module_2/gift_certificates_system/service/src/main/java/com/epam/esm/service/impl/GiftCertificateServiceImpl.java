package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.GiftCertificateToTagRelationDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.GitCertificateService;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortOperator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Gift certificate service implementation.
 */
@Log4j2
@Service
public class GiftCertificateServiceImpl extends GitCertificateService {
    private final GiftCertificateToTagRelationDaoImpl relationDao;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param giftCertificateDao    - Gift certificate DAO layer.
     * @param tagDao                - Tag DAO layer.
     * @param relationDao           - Gift certificate to tag relation DAO layer.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDaoImpl giftCertificateDao, TagDaoImpl tagDao, GiftCertificateToTagRelationDaoImpl relationDao) {
        super(giftCertificateDao, tagDao);
        this.relationDao = relationDao;
    }

    @Transactional
    @Override
    public GiftCertificate create(GiftCertificate entity) {
        String name = entity.getName();
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findByName(name);

        if(optionalGiftCertificate.isPresent()) {
            log.debug("Gift certificate with name '{}' already exists in the database.", entity.getName());
            throw new EntityExistsException("Gift certificate with name '" + entity.getName() + "' already exists in the database.");
        }

        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        entity.setCreateDate(currentLocalDateTime);
        entity.setLastUpdateDate(currentLocalDateTime);

        GiftCertificate createdGiftCertificate = giftCertificateDao.create(entity);
        createRelationsBetweenGiftCertificateAndTags(createdGiftCertificate);

        return createdGiftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        giftCertificates = getGiftCertificatesWithTags(giftCertificates);

        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findAllAndSort(ColumnName columnName, SortOperator sortOperator) {
        List<GiftCertificate> giftCertificates = findAll();

        switch (columnName) {
            case NAME:
                giftCertificates.sort(Comparator.comparing(GiftCertificate::getName));
                break;
            case CREATE_DATE:
                giftCertificates.sort(Comparator.comparing(GiftCertificate::getCreateDate));
                break;
            case LAST_UPDATE_DATE:
                giftCertificates.sort(Comparator.comparing(GiftCertificate::getLastUpdateDate));
                break;
            case WITHOUT_SORTING:
                break;
            default:
                throw new EnumConstantNotPresentException(ColumnName.class, columnName.name());
        }

        switch (sortOperator) {
            case ASC:
                giftCertificates = giftCertificates.stream().sorted().collect(Collectors.toList());
                break;
            case DESC:
                Collections.reverse(giftCertificates);
                break;
            case WITHOUT_SORTING:
                break;
            default:
                throw new EnumConstantNotPresentException(SortOperator.class, sortOperator.name());
        }

        return giftCertificates;
    }

    @Override
    public GiftCertificate findById(long id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(EntityNotFoundException::new);
        List<Tag> tagList = tagDao.findByGiftCertificateId(id);
        giftCertificate.setTags(tagList);

        return giftCertificate;
    }

    @Transactional
    @Override
    public List<GiftCertificate> findByTagName(String tagName) {
        Optional<Tag> optionalTag = tagDao.findByName(tagName);
        Tag tag = optionalTag.orElseThrow(EntityNotFoundException::new);
        long tagId = tag.getId();

        List<GiftCertificate> giftCertificates = giftCertificateDao.findByTagId(tagId);
        giftCertificates = getGiftCertificatesWithTags(giftCertificates);

        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByPartOfName(String partOfName) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByPartOfName(partOfName);
        giftCertificates = getGiftCertificatesWithTags(giftCertificates);

        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByPartOfDescription(String partOfDescription) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByPartOfDescription(partOfDescription);
        giftCertificates = getGiftCertificatesWithTags(giftCertificates);

        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByKeyword(String keyword) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByKeyword(keyword);
        giftCertificates = getGiftCertificatesWithTags(giftCertificates);

        return giftCertificates;
    }

    @Transactional
    @Override
    public boolean update(long id, GiftCertificate giftCertificate) {
        boolean result = false;

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);

        if(optionalGiftCertificate.isPresent()) {
            giftCertificate.setId(id);
            Map<String, Object> requiredFields = findRequiredFields(giftCertificate);

            if (!requiredFields.isEmpty()) {
                giftCertificate.setId(id);
                LocalDateTime currentLocalDateTime = LocalDateTime.now();
                giftCertificate.setLastUpdateDate(currentLocalDateTime);

                List<Tag> tags = giftCertificate.getTags();

                if(tags != null && !tags.isEmpty()) {
                    createRelationsBetweenGiftCertificateAndTags(giftCertificate);
                    result = giftCertificateDao.update(giftCertificate);        // Method 1
                } else {
                    result = giftCertificateDao.update(id, requiredFields);     // Method 2
                }
            }
        }

        return result;
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        boolean result = false;
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);

        if(optionalGiftCertificate.isPresent()) {
            boolean deleteTagsResult = relationDao.deleteAllTagsByGiftCertificateId(id);
            boolean deleteRelationResult = giftCertificateDao.delete(id);
            result = deleteTagsResult == deleteRelationResult;
            log.info(result ? "Gift certificate with id '{}' deleted from the database."
                    : "Gift certificate with id '{}' not deleted from the database.", id);
        }

        return result;
    }

    private void createRelationsBetweenGiftCertificateAndTags(GiftCertificate giftCertificate) {
        long giftCertificateId = giftCertificate.getId();

        giftCertificate.getTags().forEach(tag -> {
            String tagName = tag.getName();
            Optional<Tag> optionalTag = tagDao.findByName(tagName);

            // Create tag if non-existing and create for this tag relation
            if(!optionalTag.isPresent()) {
                Tag createdTag = tagDao.create(tag);
                long tagId = createdTag.getId();
                giftCertificateDao.createGiftCertificateToTagRelation(giftCertificateId, tagId);
            }
        });
    }

    private List<GiftCertificate> getGiftCertificatesWithTags(List<GiftCertificate> giftCertificates) {
         return giftCertificates.stream().peek(giftCertificate -> {
            long id = giftCertificate.getId();
            List<Tag> tags = tagDao.findByGiftCertificateId(id);
            giftCertificate.setTags(tags);
         }).collect(Collectors.toList());
    }

    private Map<String, Object> findRequiredFields(GiftCertificate giftCertificate) {
        Map<String, Object> requiredFields = new HashMap<>();

        Class<?> gc = giftCertificate.getClass();
        Field[] fields = gc.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                Object fieldValue = field.get(giftCertificate);

                if(fieldValue != null) {
                    String fieldName = field.getName();
                    requiredFields.put(fieldName, fieldValue);
                }
            } catch (IllegalAccessException e) {
                log.debug("{} is enforcing Java language access control and the underlying field is inaccessible.", field);
            }
        }

        return requiredFields;
    }
}
