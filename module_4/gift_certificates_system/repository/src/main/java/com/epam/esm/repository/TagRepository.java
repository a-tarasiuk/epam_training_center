package com.epam.esm.repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.util.MessagePropertyKey;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

import static com.epam.esm.model.util.MessagePropertyKey.*;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findById(long id);

    Optional<Tag> findByName(String name);

    @Query("SELECT t FROM Tag t JOIN GiftCertificateToTagRelation r ON r.tag = t JOIN GiftCertificate gc ON gc = r.giftCertificate WHERE gc = :giftCertificate")
    Set<Tag> findAllByGiftCertificate(@Param("giftCertificate") GiftCertificate certificate);

    /**
     * Deletes a given entity.
     *
     * @param tag must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    void delete(@NotNull(EXCEPTION_TAG_NOT_NULL) Tag tag);
}
