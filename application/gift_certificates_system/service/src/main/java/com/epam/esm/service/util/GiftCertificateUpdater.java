package com.epam.esm.service.util;

import com.epam.esm.model.entity.GiftCertificate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * Gift certificate updater.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GiftCertificateUpdater {

    public static GiftCertificate update(GiftCertificate foundCertificate, GiftCertificate updatedCertificate) {
        String foundName = foundCertificate.getName();
        String foundDescription = foundCertificate.getDescription();
        BigDecimal foundPrice = foundCertificate.getPrice();
        Integer foundDuration = foundCertificate.getDuration();

        String updatedName = updatedCertificate.getName();
        String updatedDescription = updatedCertificate.getDescription();
        BigDecimal updatedPrice = updatedCertificate.getPrice();
        Integer updatedDuration = updatedCertificate.getDuration();

        if (!ObjectUtils.isEmpty(updatedName) && !updatedName.equals(foundName)) {
            foundCertificate.setName(updatedName);
        }

        if (!ObjectUtils.isEmpty(updatedDescription) && !updatedDescription.equals(foundDescription)) {
            foundCertificate.setDescription(updatedDescription);
        }

        if (!ObjectUtils.isEmpty(updatedPrice) && !updatedPrice.equals(foundPrice)) {
            foundCertificate.setPrice(updatedPrice);
        }

        if (!ObjectUtils.isEmpty(updatedDuration) && !updatedDuration.equals(foundDuration)) {
            foundCertificate.setDuration(updatedDuration);
        }

        return foundCertificate;
    }
}