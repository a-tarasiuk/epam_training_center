package com.epam.esm.service.impl;

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
import com.epam.esm.service.exception.EntityExistingException;
import com.epam.esm.service.exception.EntityNonExistentException;
import com.epam.esm.service.util.GiftCertificateFieldChecker;
import com.epam.esm.service.util.GiftCertificateUpdater;
import com.epam.esm.service.util.PageMapper;
import com.epam.esm.service.util.PageValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.model.util.MessagePropertyKey.*;

/**
 * Gift certificate service implementation.
 */
@Log4j2
@Service
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
                    if (!relationRepository.findByGiftCertificateAndTag(createdCertificate, tag).isPresent()) {
                        relationRepository.save(new GiftCertificateToTagRelation(createdCertificate, tag));
                    }
                });

        GiftCertificateDto gcDto = modelMapper.map(createdCertificate, GiftCertificateDto.class);
        Set<TagDto> tagsDto = tagRepository.findAllByGiftCertificate(createdCertificate).stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toSet());
        gcDto.setTags(tagsDto);

        return gcDto;
    }

    @Override
    public GiftCertificateDto findById(long id) {
        GiftCertificate gc = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNonExistentException(EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, id));
        return buildGiftCertificateDtoWithTags(gc);
    }

    @Override
    public Page<GiftCertificateDto> findAll(EsmPagination pagination) {
        Pageable pageable = pageMapper.map(pagination);
        Page<GiftCertificate> certificates = certificateRepository.findAll(pageable);
        return pageMapper.map(certificates, GiftCertificateDto.class);
    }

    @Override
    public Page<GiftCertificateDto> findAll(EsmPagination pagination, GiftCertificateSearchParameter searchParameter) {
        Pageable pageable = pageMapper.map(pagination);
        Specification<GiftCertificate> specification = SpecificationGenerator.build(searchParameter);
        Page<GiftCertificate> certificates = certificateRepository.findAll(specification, pageable);

        PageValidator.validate(pagination, certificates);
        return pageMapper.map(certificates, GiftCertificateDto.class);
    }

    @Override
    public GiftCertificateDto update(long id, GiftCertificateUpdateDto certificate) {
        GiftCertificate foundGc = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNonExistentException(EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, id));

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
        GiftCertificate gc = optionalCertificate.orElseThrow(() -> new EntityNonExistentException(EXCEPTION_GIFT_CERTIFICATE_ID_NOT_FOUND, id));
        delete(gc);
    }

    private void checkIfNonExistsOrElseThrow(GiftCertificateDto certificate) {
        String name = certificate.getName();
        Optional<GiftCertificate> optionalGiftCertificate = certificateRepository.findByName(name);

        if (optionalGiftCertificate.isPresent()) {
            throw new EntityExistingException(EXCEPTION_GIFT_CERTIFICATE_NAME_EXISTS, name);
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

            if (!relationRepository.findByGiftCertificateAndTag(certificate, tag).isPresent()) {
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
        return tagRepository.findAllByGiftCertificate(certificate);
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
        relationRepository.deleteAll(relationRepository.findAllByGiftCertificate(certificate));
    }

    private void deleteIrrelevantRelations(GiftCertificate certificate, Set<Tag> tagsFromRequest) {
        Set<Tag> tagsFromDatabase = findTagsBy(certificate);
        Set<Tag> tagForRemove = new HashSet<>(tagsFromDatabase);
        tagForRemove.removeAll(tagsFromRequest);
        tagForRemove.stream()
                .map(tag -> new GiftCertificateToTagRelation(certificate, tag))
                .forEach(relationRepository::delete);
    }
}
