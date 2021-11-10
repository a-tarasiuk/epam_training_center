package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.EntityFieldName;
import com.epam.esm.util.ParameterName;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Tag DAO implementation.
 */
@Repository
public class TagDaoImpl extends TagDao {
    private static final String FIND_TAGS_BY_CERTIFICATE_SQL
            = "SELECT tag FROM Tag AS tag JOIN GiftCertificateToTagRelation AS relation ON relation.tag = tag WHERE relation.giftCertificate = :giftCertificate";

    @Override
    public Tag create(Tag tag) {
        em.persist(tag);
        return tag;
    }

    @Override
    public Set<Tag> findAllBy(GiftCertificate gc) {
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Tag> rootTag = cq.from(Tag.class);

        // JOIN GiftCertificateToTagRelation AS relation ON relation.tag = tag
        Join<Tag, GiftCertificateToTagRelation> join = rootTag.join(ParameterName.GIFT_CERTIFICATE);

        // WHERE relation.giftCertificate = :giftCertificate
        Predicate condition = cb.equal(join.get(ParameterName.GIFT_CERTIFICATE), gc);

        // Full SQL query: SELECT tag FROM Tag AS tag JOIN GiftCertificateToTagRelation AS relation ON relation.tag = tag WHERE relation.giftCertificate = :giftCertificate
        cq.select(rootTag).where(condition);

        return em.createQuery(cq)
                .getResultStream()
                .collect(Collectors.toSet());

//        return em.createQuery(FIND_TAGS_BY_CERTIFICATE_SQL, Tag.class)
//                .setParameter(ParameterName.GIFT_CERTIFICATE, gc)
//                .getResultStream()
//                .collect(Collectors.toSet());
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

    @Override
    public void delete(Tag tag) {
        em.remove(tag);
    }
}
