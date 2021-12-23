package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.dao.impl.GiftCertificateToTagRelationDaoImpl;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.exception.EntityExistingException;
import com.epam.esm.service.exception.EntityNonExistentException;
import com.epam.esm.model.pojo.MostWidelyUsedTag;
import com.epam.esm.model.pojo.UserInformation;
import com.epam.esm.service.TagService;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.model.util.MessagePropertyKey;
import org.apache.commons.lang3.math.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ModelMapper modelMapper;
    private final TagDao tagDao;
    private final UserDao userDao;
    private final GiftCertificateDao gcDao;
    private final GiftCertificateToTagRelationDaoImpl relationDao;

    /**
     * Instantiates a new tag service.
     *
     * @param tagDao - Tag DAO layer.
     */
    @Autowired
    public TagServiceImpl(ModelMapper modelMapper, TagDao tagDao, UserDao userDao, GiftCertificateDao gcDao, GiftCertificateToTagRelationDaoImpl relationDao) {
        this.modelMapper = modelMapper;
        this.tagDao = tagDao;
        this.userDao = userDao;
        this.gcDao = gcDao;
        this.relationDao = relationDao;
    }

    @Override
    public TagDto create(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        checkIfTagExistsOrElseThrow(tag);
        Tag createdTag = tagDao.create(tag);
        return modelMapper.map(createdTag, TagDto.class);
    }

    @Override
    public Set<TagDto> findAll(EsmPagination pagination) {
        return tagDao.findAll(pagination, Tag.class).stream()
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public TagDto findById(long id) {
        return tagDao.findById(id)
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .orElseThrow(() -> new EntityNonExistentException(MessagePropertyKey.EXCEPTION_TAG_ID_NOT_FOUND, id));
    }

    @Override
    public void delete(long id) {
        Tag tag = tagDao.findById(id)
                .orElseThrow(() -> new EntityNonExistentException(MessagePropertyKey.EXCEPTION_TAG_ID_NOT_FOUND, id));
        delete(tag);
    }

    @Override
    public Set<MostWidelyUsedTag> findMostWidelyUsedTags() {
        return userDao.findUsersWithHighestCostOfAllOrders().stream()
                .flatMap(up -> Stream.of(up)
                        .map(UserInformation::getUser)
                        .map(this::findAllGiftCertificatesByUser)
                        .map(this::findAllTagsFromGiftCertificates)
                        .map(this::findCountOfRepetitionsOfEachTag)
                        .map(map -> buildMostWidelyUsedTag(up, map)))
                .collect(Collectors.toSet());
    }

    private List<GiftCertificate> findAllGiftCertificatesByUser(User user) {
        return gcDao.findBy(user);
    }

    private List<Tag> findAllTagsFromGiftCertificates(List<GiftCertificate> certificates) {
        return certificates.stream()
                .map(tagDao::findAllBy)
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

    private MostWidelyUsedTag buildMostWidelyUsedTag(UserInformation up, Map<Tag, Long> map) {
        Long count = findMaxCountOfRepetitions(map);
        Set<Tag> tags = findMostWidelyUsedTags(map, count);

        return new MostWidelyUsedTag()
                .setTags(tags)
                .setNumberOfUsesTags(count)
                .setUserInformation(up);
    }

    private void delete(Tag tag) {
        deleteAllRelations(tag);
        tagDao.delete(tag);
    }

    private void deleteAllRelations(Tag tag) {
        relationDao.findAllBy(tag).forEach(relationDao::delete);
    }

    private void checkIfTagExistsOrElseThrow(Tag tag) {
        String name = tag.getName();
        tagDao.findByName(name).ifPresent(t -> {
            throw new EntityExistingException(MessagePropertyKey.EXCEPTION_TAG_NAME_EXISTS, name);
        });
    }
}
