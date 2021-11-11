package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateToTagRelationDaoImpl;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.AbstractService;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.pagination.EsmPagination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Tag service implementation.
 */
@Service
@Transactional
public class TagServiceImpl implements AbstractService<TagDto> {
    private final ModelMapper modelMapper;
    private final TagDao tagDao;
    private final OrderDao orderDao;
    private final GiftCertificateDao gcDao;
    private final GiftCertificateToTagRelationDaoImpl relationDao;

    /**
     * Instantiates a new tag service.
     *
     * @param tagDao - Tag DAO layer.
     */
    @Autowired
    public TagServiceImpl(ModelMapper modelMapper, TagDao tagDao, OrderDao orderDao, GiftCertificateDao gcDao, GiftCertificateToTagRelationDaoImpl relationDao) {
        this.modelMapper = modelMapper;
        this.tagDao = tagDao;
        this.orderDao = orderDao;
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
    public Set<TagDto> findAll(EsmPagination esmPagination) {
        return tagDao.findAll(esmPagination, Tag.class).stream()
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
        Tag tag = tagDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessagePropertyKey.EXCEPTION_TAG_ID_NOT_FOUND));

        // Delete all relations
        relationDao.findAllBy(tag).forEach(relationDao::delete);

        // Delete tag
        tagDao.delete(tag);
    }

    public Map<Tag, User> findMostWidelyUsedTags() {
        // Find users with the highest cost of all orders
        Set<User> users = orderDao.findUsersWithHighestCostOfAllOrders();

        // Find all gift certificates by users
        Map<User, Set<GiftCertificate>> userGcMap = findAllGiftCertificatesByUsers(users);
        Map<Tag, User> tagUserMap = new HashMap<>();

        // Find all tags by gift certificates
        for (Map.Entry<User, Set<GiftCertificate>> entry : userGcMap.entrySet()) {
            Set<GiftCertificate> gcs = entry.getValue();

            // Find all tags by all gift certificates
            List<Tag> tags = gcs.stream()
                    .map(tagDao::findAllBy)
                    .flatMap(Set::stream)
                    .collect(Collectors.toList());

            // Find count of repetitions of each tag
            Map<Tag, Long> tagCount = tags.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            // Find most usage tags
            Tag tag = Collections.max(tagCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            User user = entry.getKey();

            tagUserMap.put(tag, user);
        }

        return tagUserMap;
    }

    private Map<User, Set<GiftCertificate>> findAllGiftCertificatesByUsers(Set<User> users) {
        return users.stream()
                .collect(Collectors.toMap(user -> user, gcDao::findBy));
    }

    private void checkIfTagExistsOrElseThrow(Tag tag) {
        String tagName = tag.getName();
        tagDao.findByName(tagName).ifPresent(t -> {
            throw new EntityExistsException(MessagePropertyKey.EXCEPTION_TAG_NAME_EXISTS);
        });
    }
}
