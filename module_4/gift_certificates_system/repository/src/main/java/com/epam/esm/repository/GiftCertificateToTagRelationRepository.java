package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.entity.Tag;
import org.springframework.data.repository.Repository;

import javax.persistence.IdClass;
import java.util.List;

public interface GiftCertificateToTagRelationRepository extends Repository<GiftCertificateToTagRelation, Long> {

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
}
