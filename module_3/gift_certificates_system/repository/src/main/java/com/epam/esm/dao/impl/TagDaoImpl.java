package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.ParameterName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Tag DAO implementation.
 */
@Repository
public class TagDaoImpl extends TagDao {
    private final GiftCertificateToTagRelationDaoImpl relationDao;

    @Autowired
    public TagDaoImpl(GiftCertificateToTagRelationDaoImpl relationDao) {
        this.relationDao = relationDao;
    }

    /**
     * Full query:<br>
     * SELECT tag FROM Tag AS tag JOIN GiftCertificateToTagRelation AS relation ON relation.tag = tag WHERE relation.giftCertificate = :giftCertificate
     */
    @Override
    public Set<Tag> findAllBy(GiftCertificate certificate) {
        return relationDao.findAllBy(certificate).stream()
                .map(GiftCertificateToTagRelation::getTag)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Tag> findById(long id) {
        Tag tag = em.find(Tag.class, id);
        return Optional.ofNullable(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Optional<Tag> optionalTag;

        try {
            CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
            Root<Tag> fromTag = cq.from(Tag.class);
            Predicate condition = cb.equal(fromTag.get(ParameterName.NAME), name);
            cq.select(fromTag).where(condition);

            Tag tag = em.createQuery(cq)
                    .getSingleResult();

            optionalTag = Optional.of(tag);
        } catch (NoResultException e) {
            optionalTag = Optional.empty();
        }

        return optionalTag;
    }
}
