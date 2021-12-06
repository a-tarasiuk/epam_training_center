package com.epam.esm.repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

import static com.epam.esm.repository.util.ParameterName.GIFT_CERTIFICATE;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query("SELECT t FROM Tag t JOIN GiftCertificateToTagRelation r ON r.tag = t JOIN GiftCertificate gc ON gc = r.giftCertificate WHERE gc = :giftCertificate")
    Set<Tag> findAllByGiftCertificate(@Param(GIFT_CERTIFICATE) GiftCertificate certificate);
}
