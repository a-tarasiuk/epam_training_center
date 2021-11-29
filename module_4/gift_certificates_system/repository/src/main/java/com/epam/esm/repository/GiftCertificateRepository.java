package com.epam.esm.repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_GIFT_CERTIFICATE_NOT_NULL;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {
    Optional<GiftCertificate> findByName(String name);

    @Query("SELECT g FROM GiftCertificate AS g JOIN GiftCertificateToTagRelation AS r ON r.giftCertificate = g JOIN Tag AS t ON t = r.tag WHERE t = :tag")
    List<GiftCertificate> findAllByTag(@Param("tag") Tag tag);

    @Query("SELECT g FROM  GiftCertificate AS g JOIN Order AS o ON o.giftCertificate = g WHERE o.user = :user")
    List<GiftCertificate> findAllByUser(@Param("user") User user);

    void delete(@NotNull(EXCEPTION_GIFT_CERTIFICATE_NOT_NULL) GiftCertificate certificate);
}
