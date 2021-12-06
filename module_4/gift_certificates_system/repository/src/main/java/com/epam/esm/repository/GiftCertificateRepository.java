package com.epam.esm.repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {
    Optional<GiftCertificate> findByName(String name);

    @Query("SELECT g FROM  GiftCertificate AS g JOIN Order AS o ON o.giftCertificate = g WHERE o.user = :user")
    List<GiftCertificate> findAllByUser(@Param("user") User user);
}
