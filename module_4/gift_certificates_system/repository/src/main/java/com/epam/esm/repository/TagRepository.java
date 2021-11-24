package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
    Optional<Tag> findById(long id);

    Optional<Tag> findByName(String name);

    @Query("SELECT t FROM Tag t JOIN GiftCertificateToTagRelation r ON r.tag = t JOIN GiftCertificate gc WHERE gc = :giftCertificate")
    Set<Tag> findAllByGiftCertificate(@Param("giftCertificate") GiftCertificate certificate);

    /**
     * Deletes a given entity.
     *
     * @param tag must not be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    void delete(Tag tag);
}
