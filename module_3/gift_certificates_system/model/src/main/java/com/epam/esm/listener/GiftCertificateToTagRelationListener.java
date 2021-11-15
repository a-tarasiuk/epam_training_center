package com.epam.esm.listener;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateToTagRelation;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import java.time.LocalDateTime;

public final class GiftCertificateToTagRelationListener {
    private static LocalDateTime currentDateTime;

    @PrePersist
    public void beforeCreate(GiftCertificateToTagRelation relation) {
        setGiftCertificateLastUpdateTime(relation);
    }

    @PreRemove
    public void beforeRemove(GiftCertificateToTagRelation relation) {
        setGiftCertificateLastUpdateTime(relation);
    }

    private void setGiftCertificateLastUpdateTime(GiftCertificateToTagRelation relation) {
        currentDateTime = LocalDateTime.now();
        GiftCertificate giftCertificate = relation.getGiftCertificate();
        giftCertificate.setLastUpdateDate(currentDateTime);
    }
}
