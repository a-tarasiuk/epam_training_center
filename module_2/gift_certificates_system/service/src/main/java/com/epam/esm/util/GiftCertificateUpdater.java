package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Gift certificate updater.
 */
public class GiftCertificateUpdater {
    private GiftCertificateUpdater() {
    }

    public static GiftCertificate update(GiftCertificate foundGiftCertificate, GiftCertificate newGiftCertificate) {
        String oldName = foundGiftCertificate.getName();
        String oldDescription = foundGiftCertificate.getDescription();
        BigDecimal oldPrice = foundGiftCertificate.getPrice();
        Integer oldDuration = foundGiftCertificate.getDuration();

        String newName = newGiftCertificate.getName();
        String newDescription = newGiftCertificate.getDescription();
        BigDecimal newPrice = newGiftCertificate.getPrice();
        Integer newDuration = newGiftCertificate.getDuration();

        if (!ObjectUtils.isEmpty(newName) && !newName.equals(oldName)) {
            foundGiftCertificate.setName(newName);
        }

        if (!ObjectUtils.isEmpty(newDescription) && !newDescription.equals(oldDescription)) {
            foundGiftCertificate.setDescription(newDescription);
        }

        if (!ObjectUtils.isEmpty(newPrice) && !newPrice.equals(oldPrice)) {
            foundGiftCertificate.setPrice(newPrice);
        }

        if (!ObjectUtils.isEmpty(newDuration) && !newDuration.equals(oldDuration)) {
            foundGiftCertificate.setDuration(newDuration);
        }

        LocalDateTime updateDate = LocalDateTime.now();
        foundGiftCertificate.setLastUpdateDate(updateDate);
        foundGiftCertificate.setTags(newGiftCertificate.getTags());

        return foundGiftCertificate;
    }
}