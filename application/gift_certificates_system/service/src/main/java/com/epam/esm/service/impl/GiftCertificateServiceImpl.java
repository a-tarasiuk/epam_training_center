package com.epam.esm.service.impl;

<<<<<<< HEAD
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateToTagRelationDaoImpl;
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
=======
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.GiftCertificateUpdateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.GiftCertificateToTagRelation;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.pojo.GiftCertificateSearchParameter;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateToTagRelationRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.repository.util.SpecificationGenerator;
import com.epam.esm.service.GitCertificateService;
import com.epam.esm.service.exception.EntityExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.util.GiftCertificateFieldChecker;
import com.epam.esm.service.util.GiftCertificateUpdater;
import com.epam.esm.service.util.PageMapper;
import com.epam.esm.service.util.PageValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
>>>>>>> module_6
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

<<<<<<< HEAD
import static com.epam.esm.util.PropertyKey.MESSAGE_DESCRIPTION_INVALID_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_ENTITY_EXISTS_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_ENTITY_INVALID_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_ENTITY_NOT_FOUND_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_KEYWORD_INVALID_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_NAME_INVALID_EXCEPTION;
import static com.epam.esm.util.PropertyKey.MESSAGE_REQUIRED_FIELDS_EMPTY_EXCEPTION;
=======
import static com.epam.esm.model.util.MessagePropertyKey.*;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_NAME_EXISTS;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_UPDATE_FIELDS_EMPTY;
>>>>>>> module_6

/**
 * Gift certificate service implementation.
 */
@Log4j2
@Service
<<<<<<< HEAD
public class GiftCertificateServiceImpl implements GitCertificateService {
    private final TagDao tagDao;
    private final GiftCertificateDao giftCertificateDao;
    private final GiftCertificateToTagRelationDaoImpl relationDao;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param giftCertificateDao - Gift certificate DAO layer.
     * @param tagDao             - Tag DAO layer.
     * @param relationDao        - Gift certificate to tag relation DAO layer.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, GiftCertificateToTagRelationDaoImpl relationDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.relationDao = relationDao;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public GiftCertificate create(GiftCertificate entity) {
        Set<Tag> tags = entity.getTags();
        if (!GiftCertificateValidator.isValid(entity) || !TagValidator.isValid(tags)) {
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
        Set<Tag> tagList = tagDao.findByGiftCertificateId(id);
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
    public GiftCertificate update(long id, GiftCertificate giftCertificate) {
        if (!requiredFieldsAreEmpty(giftCertificate)) {
            GiftCertificate foundGiftCertificate = giftCertificateDao.findById(id).orElseThrow(() -> new EntityNotFoundException(MESSAGE_ENTITY_NOT_FOUND_EXCEPTION));
            giftCertificate = GiftCertificateUpdater.update(foundGiftCertificate, giftCertificate);

            if (GiftCertificateValidator.isValid(giftCertificate)) {
                Set<Tag> tags = giftCertificate.getTags();

                if (!ObjectUtils.isEmpty(tags)) {
                    if (tags.stream().allMatch(TagValidator::isValid)) {
                        deleteIrrelevantRelations(giftCertificate);
                        createRelations(giftCertificate);
                    } else {
                        throw new EntityInvalidException(MESSAGE_ENTITY_INVALID_EXCEPTION);
                    }
                }

                GiftCertificate updated = giftCertificateDao.update(id, giftCertificate);
                findAndSetTags(updated);

                return updated;
            } else {
                throw new EntityInvalidException(MESSAGE_ENTITY_INVALID_EXCEPTION);
            }
        } else {
            throw new FieldInvalidException(MESSAGE_REQUIRED_FIELDS_EMPTY_EXCEPTION);
        }
    }

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
            Set<Tag> tags = tagDao.findByGiftCertificateId(id);
            giftCertificate.setTags(tags);
        });
    }

    private void findAndSetTags(GiftCertificate giftCertificate) {
        long id = giftCertificate.getId();
        Set<Tag> tags = tagDao.findByGiftCertificateId(id);
        giftCertificate.setTags(tags);
=======
@Transactional
public class GiftCertificateServiceImpl implements GitCertificateService {
    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateToTagRelationRepository relationRepository;
    private final ModelMapper modelMapper;
    private final PageMapper pageMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository, TagRepository tagRepository,
                                      GiftCertificateToTagRelationRepository relationRepository,
                                      ModelMapper modelMapper, PageMapper pageMapper) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.relationRepository = relationRepository;
        this.modelMapper = modelMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto certificateDto) {
        checkIfNonExistsOrElseThrow(certificateDto);

        GiftCertificate certificate = modelMapper.map(certificateDto, GiftCertificate.class);
        GiftCertificate createdCertificate = certificateRepository.save(certificate);

        certificateDto.getTags().stream()
                .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                .map(tag -> tagRepository.findByName(tag.getName()).orElseGet(() -> tagRepository.save(tag)))
                .forEach(tag -> {
                    if (!relationRepository.findByGiftCertificateIdAndTagId(createdCertificate.getId(), tag.getId()).isPresent()) {
                        relationRepository.save(new GiftCertificateToTagRelation(createdCertificate, tag));
                    }
                });

        GiftCertificateDto gcDto = modelMapper.map(createdCertificate, GiftCertificateDto.class);
        Set<TagDto> tagsDto = tagRepository.findAllByGiftCertificateId(createdCertificate.getId()).stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toSet());
        gcDto.setTags(tagsDto);

        log.info("Gift certificate successfully created: {}", gcDto);
        return gcDto;
    }

    @Override
    public GiftCertificateDto findById(long id) {
        GiftCertificate gc = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, id));

        log.info("Gift certificate with ID {} found in the database.", id);
        return buildGiftCertificateDtoWithTags(gc);
    }

    @Override
    public Page<GiftCertificateDto> findAll(EsmPagination pagination) {
        Pageable pageable = pageMapper.map(pagination);
        Page<GiftCertificate> certificates = certificateRepository.findAll(pageable);

        log.info("Successfully was found {} certificates.", certificates.getTotalElements());
        return pageMapper.map(certificates, GiftCertificateDto.class);
    }

    @Override
    public Page<GiftCertificateDto> findAll(EsmPagination pagination, GiftCertificateSearchParameter searchParameter) {
        Pageable pageable = pageMapper.map(pagination);
        Specification<GiftCertificate> specification = SpecificationGenerator.build(searchParameter);
        Page<GiftCertificate> certificates = certificateRepository.findAll(specification, pageable);

        if (certificates.getTotalPages() != NumberUtils.INTEGER_ZERO) {
            PageValidator.validate(pagination, certificates);

            log.info("Successfully was found {} certificates.", certificates.getTotalElements());
            return pageMapper.map(certificates, GiftCertificateDto.class);
        } else {
            throw new javax.persistence.EntityNotFoundException(EXCEPTION_GIFT_CERTIFICATE_WITH_SEARCH_PARAMETERS);
        }
    }

    @Override
    public GiftCertificateDto update(long id, GiftCertificateUpdateDto certificate) {
        GiftCertificate foundGc = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, id));

        if (GiftCertificateFieldChecker.isFilledOneField(certificate)) {
            GiftCertificate updatedGc = modelMapper.map(certificate, GiftCertificate.class);

            GiftCertificate updated = GiftCertificateUpdater.update(foundGc, updatedGc);
            GiftCertificate created = certificateRepository.save(updated);  // update

            if (ObjectUtils.isNotEmpty(certificate.getTags())) {
                updateRelations(created, certificate);
            }

            return buildGiftCertificateDtoWithTags(created);
        } else {
            throw new IllegalArgumentException(EXCEPTION_GIFT_CERTIFICATE_UPDATE_FIELDS_EMPTY);
        }
    }

    @Override
    public void delete(long id) {
        Optional<GiftCertificate> optionalCertificate = certificateRepository.findById(id);
        GiftCertificate gc = optionalCertificate.orElseThrow(() -> new EntityNotFoundException(EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, id));
        delete(gc);

        log.info("Gift certificate with ID {} successfully deleted.", id);
    }

    private void checkIfNonExistsOrElseThrow(GiftCertificateDto certificate) {
        String name = certificate.getName();
        Optional<GiftCertificate> optionalGiftCertificate = certificateRepository.findByName(name);

        if (optionalGiftCertificate.isPresent()) {
            throw new EntityExistsException(EXCEPTION_GIFT_CERTIFICATE_NAME_EXISTS, name);
        }
    }

    private void updateRelations(GiftCertificate certificate, GiftCertificateUpdateDto updatedCertificate) {
        Set<Tag> tagsFromRequest = findTagsBy(updatedCertificate);
        tagsFromRequest.forEach(tag -> createRelationIfNonExist(certificate, tag));
        deleteIrrelevantRelations(certificate, tagsFromRequest);
    }

    private void createRelationIfNonExist(GiftCertificate certificate, Tag tag) {
        String tagName = tag.getName();
        Optional<Tag> optionalTag = tagRepository.findByName(tagName);

        if (optionalTag.isPresent()) {
            Tag foundTag = optionalTag.get();

            long certificateId = certificate.getId();
            long tagId = foundTag.getId();

            if (!relationRepository.findByGiftCertificateIdAndTagId(certificateId, tagId).isPresent()) {
                GiftCertificateToTagRelation relation = new GiftCertificateToTagRelation(certificate, foundTag);
                relationRepository.save(relation);
            }
        } else {
            Tag createdTag = tagRepository.save(tag);
            GiftCertificateToTagRelation relation = new GiftCertificateToTagRelation(certificate, createdTag);
            relationRepository.save(relation);
        }
    }

    private Set<Tag> findTagsBy(GiftCertificate certificate) {
        long id = certificate.getId();
        return tagRepository.findAllByGiftCertificateId(id);
    }

    private Set<TagDto> findTagsDtoBy(GiftCertificate certificate) {
        return findTagsBy(certificate).stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toSet());
    }

    private Set<Tag> findTagsBy(GiftCertificateUpdateDto certificate) {
        return certificate.getTags().stream()
                .map(tagDto -> modelMapper.map(tagDto, Tag.class))
                .collect(Collectors.toSet());
    }

    private GiftCertificateDto buildGiftCertificateDtoWithTags(GiftCertificate certificate) {
        Set<TagDto> tagsDto = findTagsDtoBy(certificate);
        GiftCertificateDto gcDto = modelMapper.map(certificate, GiftCertificateDto.class);
        gcDto.setTags(tagsDto);
        return gcDto;
    }

    private void delete(GiftCertificate certificate) {
        deleteRelations(certificate);
        certificateRepository.delete(certificate);
    }

    private void deleteRelations(GiftCertificate certificate) {
        long id = certificate.getId();
        relationRepository.deleteAll(relationRepository.findAllByGiftCertificateId(id));
    }

    private void deleteIrrelevantRelations(GiftCertificate certificate, Set<Tag> tagsFromRequest) {
        Set<Tag> tagsFromDatabase = findTagsBy(certificate);
        Set<Tag> tagForRemove = new HashSet<>(tagsFromDatabase);
        tagForRemove.removeAll(tagsFromRequest);
        tagForRemove.stream()
                .map(tag -> new GiftCertificateToTagRelation(certificate, tag))
                .forEach(relationRepository::delete);
>>>>>>> module_6
    }
}
