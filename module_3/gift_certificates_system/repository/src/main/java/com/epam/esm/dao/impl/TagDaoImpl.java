package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.DatabaseColumnName;
import com.epam.esm.util.EntityFieldName;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Tag DAO implementation.
 */
@Repository
public class TagDaoImpl implements TagDao {
    private static final String FIND_ALL_TAGS_SQL
            = "SELECT tag FROM Tag tag";
    private static final String FIND_TAG_BY_NAME_SQL
            = "SELECT tag FROM Tag tag WHERE tag.name = :tagName";
    private static final String FIND_TAGS_BY_CERTIFICATE_SQL
            = "SELECT tag FROM Tag AS tag JOIN GiftCertificateToTagRelation AS relation ON relation.tag = tag WHERE relation.giftCertificate = :giftCertificate";
    private static final String DELETE_GIFT_CERTIFICATE_TO_TAG_RELATION_BY_ID_SQL
            = "DELETE FROM GiftCertificateToTagRelation AS relation WHERE relation.giftCertificate = :giftCertificateId";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Set<Tag> findAll() {
        return entityManager.createQuery(FIND_ALL_TAGS_SQL, Tag.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Tag> findById(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        return Optional.ofNullable(tag);
    }

    @Override
    public Optional<Tag> findByName(String tagName) {
        Optional<Tag> optionalTag;

        try {
            optionalTag = Optional.of(entityManager.createQuery(FIND_TAG_BY_NAME_SQL, Tag.class)
                    .setParameter(EntityFieldName.TAG_NAME, tagName)
                    .getSingleResult());
        } catch (NoResultException e) {
            optionalTag = Optional.empty();
        }

        return optionalTag;
    }

    @Override
    public void delete(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public Set<Tag> findAll(GiftCertificate gc) {
        return entityManager.createQuery(FIND_TAGS_BY_CERTIFICATE_SQL, Tag.class)
                .setParameter(EntityFieldName.GIFT_CERTIFICATE, gc)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Tag> findByGiftCertificateId(long id) {
        return new HashSet<>(entityManager.createQuery(FIND_TAGS_BY_CERTIFICATE_SQL, Tag.class)
                .setParameter(EntityFieldName.GIFT_CERTIFICATE_ID, id)
                .getResultList());
    }

    @Override
    public void deleteAllTagsByGiftCertificateId(long id) {
        Query query = entityManager.createNativeQuery(DELETE_GIFT_CERTIFICATE_TO_TAG_RELATION_BY_ID_SQL);
        query.setParameter(EntityFieldName.GIFT_CERTIFICATE_ID, id);
        query.executeUpdate();
    }
}
