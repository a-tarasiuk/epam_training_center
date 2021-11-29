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
     * @param tag Tag entity.
     * @return List of gift certificate to tag relation.
     */
    List<GiftCertificateToTagRelation> findAllByTag(Tag tag);

    /**
     * Find all gift certificate to tag relation by gift certificate entity.
     *
     * @param certificate Gift certificate entity.
     * @return List of gift certificate to tag relation.
     */
    List<GiftCertificateToTagRelation> findAllByGiftCertificate(GiftCertificate certificate);

    /**
     * Returns whether an entity with the given exists.
     *
     * @param relation must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    Optional<GiftCertificateToTagRelation> findByGiftCertificateAndTag(GiftCertificate certificate, Tag tag);
}
