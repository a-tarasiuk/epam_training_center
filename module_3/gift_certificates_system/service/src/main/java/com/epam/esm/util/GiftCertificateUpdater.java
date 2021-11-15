package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * Gift certificate updater.
 */
public class GiftCertificateUpdater {
    private GiftCertificateUpdater() {
    }

    public static GiftCertificate update(GiftCertificate found, GiftCertificate updated) {
        String foundName = found.getName();
        String foundDescription = found.getDescription();
        BigDecimal foundPrice = found.getPrice();
        Integer foundDuration = found.getDuration();

        String updatedName = updated.getName();
        String updatedDescription = updated.getDescription();
        BigDecimal updatedPrice = updated.getPrice();
        Integer updatedDuration = updated.getDuration();

        if (!ObjectUtils.isEmpty(updatedName) && !updatedName.equals(foundName)) {
            found.setName(updatedName);
        }

        if (!ObjectUtils.isEmpty(updatedDescription) && !updatedDescription.equals(foundDescription)) {
            found.setDescription(updatedDescription);
        }

        if (!ObjectUtils.isEmpty(updatedPrice) && !updatedPrice.equals(foundPrice)) {
            found.setPrice(updatedPrice);
        }

        if (!ObjectUtils.isEmpty(updatedDuration) && !updatedDuration.equals(foundDuration)) {
            found.setDuration(updatedDuration);
        }

        return found;
    }
}