package com.epam.esm.repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.GiftCertificateToTagRelation;
import com.epam.esm.model.entity.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateToTagRelationRepository extends CrudRepository<GiftCertificateToTagRelation, Long> {

    /**
     * Find all gift certificate to tag relation by tag entity.
     *
     * @param id Tag id.
     * @return List of gift certificate to tag relation.
     */
    List<GiftCertificateToTagRelation> findAllByTagId(long id);

    /**
     * Find all gift certificate to tag relation by gift certificate entity.
     *
     * @param id Gift certificate id.
     * @return List of gift certificate to tag relation.
     */
    List<GiftCertificateToTagRelation> findAllByGiftCertificate(long id);

    /**
     * Returns whether an entity with the given exists.
     *
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    Optional<GiftCertificateToTagRelation> findByGiftCertificateAndTag(long giftCertificateId, long tagId);
}
