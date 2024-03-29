package com.epam.esm.service.util;

import com.epam.esm.model.dto.GiftCertificateUpdateDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GiftCertificateFieldChecker {
    public static boolean isFilledOneField(GiftCertificateUpdateDto certificate) {
        String name = certificate.getName();
        String description = certificate.getDescription();
        BigDecimal price = certificate.getPrice();
        Integer duration = certificate.getDuration();

        return ObjectUtils.isNotEmpty(name)
                || ObjectUtils.isNotEmpty(description)
                || ObjectUtils.isNotEmpty(price)
                || ObjectUtils.isNotEmpty(duration);
    }
}
