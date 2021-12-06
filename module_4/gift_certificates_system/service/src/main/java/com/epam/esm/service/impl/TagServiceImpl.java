package com.epam.esm.service.impl;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.pojo.MostWidelyUsedTag;
import com.epam.esm.model.pojo.UserInformation;
import com.epam.esm.model.util.MessagePropertyKey;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateToTagRelationRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.EntityExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.util.PageMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Tag service implementation.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService<TagDto> {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository certificateRepository;
    private final GiftCertificateToTagRelationRepository relationRepository;
    private final ModelMapper modelMapper;
    private final PageMapper pageMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, UserRepository userRepository,
                          GiftCertificateRepository certificateRepository, GiftCertificateToTagRelationRepository relationRepository,
                          ModelMapper modelMapper, PageMapper pageMapper) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
        this.relationRepository = relationRepository;
        this.modelMapper = modelMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    public TagDto create(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        checkIfTagExistsOrElseThrow(tag);
        Tag createdTag = tagRepository.save(tag);
        return modelMapper.map(createdTag, TagDto.class);
    }

    @Override
    public Page<TagDto> findAll(EsmPagination pagination) {
        Pageable pageable = pageMapper.map(pagination);
        Page<Tag> tags = tagRepository.findAll(pageable);
        return pageMapper.map(tags, TagDto.class);
    }

    @Override
    public TagDto findById(long id) {
        return tagRepository.findById(id)
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new EntityNotFoundException(MessagePropertyKey.EXCEPTION_TAG_ID_NOT_FOUND, id));
    }

    @Override
    public void delete(long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessagePropertyKey.EXCEPTION_TAG_ID_NOT_FOUND, id));
        delete(tag);
    }

    @Override
    public Set<MostWidelyUsedTag> findMostWidelyUsedTagsOfTopUser() {
        return userRepository.findUsersWithHighestCostOfAllOrders().stream()
                .flatMap(userInformation -> Stream.of(userInformation)
                        .map(UserInformation::getUser)
                        .map(this::findAllGiftCertificatesByUser)
                        .map(this::findAllTagsFromGiftCertificates)
                        .map(this::findCountOfRepetitionsOfEachTag)
                        .map(map -> buildMostWidelyUsedTag(userInformation, map)))
                .collect(Collectors.toSet());
    }

    private List<GiftCertificate> findAllGiftCertificatesByUser(User user) {
        return certificateRepository.findAllByUser(user);
    }

    private List<Tag> findAllTagsFromGiftCertificates(List<GiftCertificate> certificates) {
        return certificates.stream()
                .map(tagRepository::findAllByGiftCertificate)
                .flatMap(Set::stream)
                .collect(Collectors.toList());
    }

    private Map<Tag, Long> findCountOfRepetitionsOfEachTag(List<Tag> tags) {
        return tags.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private Long findMaxCountOfRepetitions(Map<Tag, Long> map) {
        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue)
                .orElse(NumberUtils.LONG_ZERO);
    }

    private Set<Tag> findMostWidelyUsedTags(Map<Tag, Long> map, Long maxRepetitions) {
        return map.entrySet().stream()
                .filter(e -> e.getValue().equals(maxRepetitions))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private MostWidelyUsedTag buildMostWidelyUsedTag(UserInformation userInformation, Map<Tag, Long> map) {
        Long count = findMaxCountOfRepetitions(map);
        Set<Tag> tags = findMostWidelyUsedTags(map, count);
        User user = userInformation.getUser();
        UserDto userDto = modelMapper.map(user, UserDto.class);

        return MostWidelyUsedTag.builder()
                .userDto(userDto)
                .tags(tags)
                .numberOfUses(count)
                .sumOfAllOrders(userInformation.getSumOfAllOrders())
                .build();
    }

    private void delete(Tag tag) {
        deleteAllRelations(tag);
        tagRepository.delete(tag);
    }

    private void deleteAllRelations(Tag tag) {
        relationRepository.deleteAll(relationRepository.findAllByTag(tag));
    }

    private void checkIfTagExistsOrElseThrow(Tag tag) {
        String name = tag.getName();
        tagRepository.findByName(name).ifPresent(t -> {
            throw new EntityExistsException(MessagePropertyKey.EXCEPTION_TAG_NAME_EXISTS, name);
        });
    }
}
