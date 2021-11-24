package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.pojo.GiftCertificateSearchParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository extends PagingAndSortingRepository<GiftCertificate, Long> {
    Optional<GiftCertificate> findByName(String name);

    @Query("SELECT g FROM GiftCertificate AS g JOIN GiftCertificateToTagRelation AS r ON r.giftCertificate = g JOIN Tag AS t ON t = r.tag WHERE t = :tag")
    List<GiftCertificate> findAllByTag(@Param("tag") Tag tag);

    @Query("SELECT g FROM  GiftCertificate AS g JOIN Order AS o ON o.giftCertificate = g WHERE o.user = :user")
    List<GiftCertificate> findAllByUser(@Param("user") User user);

    void delete(GiftCertificate certificate);

    @Query("SELECT g FROM  GiftCertificate g")         //todo
    Page<GiftCertificate> findAll(GiftCertificateSearchParameter parameter, Pageable pageable);
}
