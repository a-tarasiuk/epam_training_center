package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import java.time.LocalDateTime;

/**
 * Gift certificate updater.
 */
public class GiftCertificateUpdater {
    private GiftCertificateUpdater() {
    }

    public static GiftCertificate update(GiftCertificate oldGiftCertificate, GiftCertificate newGiftCertificate) {
        long id = oldGiftCertificate.getId();
        newGiftCertificate.setId(id);

        String oldName = newGiftCertificate.getName();
        String oldDescription = newGiftCertificate.getName();
        float oldPrice = newGiftCertificate.getPrice();
        int oldDuration = newGiftCertificate.getDuration();

        String newName = newGiftCertificate.getName();
        String newDescription = newGiftCertificate.getName();
        float newPrice = newGiftCertificate.getPrice();
        int newDuration = newGiftCertificate.getDuration();

        if(newName != null && !newName.isEmpty() && !newName.equals(oldName)) {
            oldGiftCertificate.setName(newName);
        }

        if(newDescription != null && !newDescription.isEmpty() && !newDescription.equals(oldDescription)) {
            oldGiftCertificate.setDescription(newDescription);
        }

        if(newPrice != oldPrice) {
            oldGiftCertificate.setPrice(newPrice);
        }

        if(newDuration != oldDuration) {
            oldGiftCertificate.setDuration(newDuration);
        }

        LocalDateTime updateDate = LocalDateTime.now();
        oldGiftCertificate.setLastUpdateDate(updateDate);

        return oldGiftCertificate;
    }
}
