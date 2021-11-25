package com.epam.esm.model.listener;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.GiftCertificateToTagRelation;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import java.time.LocalDateTime;

public final class GiftCertificateToTagRelationListener {
    @PrePersist
    public void beforeCreate(GiftCertificateToTagRelation relation) {
        setGiftCertificateLastUpdateTime(relation);
    }

    @PreRemove
    public void beforeRemove(GiftCertificateToTagRelation relation) {
        setGiftCertificateLastUpdateTime(relation);
    }

    private void setGiftCertificateLastUpdateTime(GiftCertificateToTagRelation relation) {
        GiftCertificate giftCertificate = relation.getGiftCertificate();
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
    }
}
