package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateUpdateDto;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;

public class GiftCertificateFieldChecker {
    public static boolean isFilledOneField(GiftCertificateUpdateDto gc) {
        String name = gc.getName();
        String description = gc.getDescription();
        BigDecimal price = gc.getPrice();
        Integer duration = gc.getDuration();

        return ObjectUtils.isNotEmpty(name)
                || ObjectUtils.isNotEmpty(description)
                || ObjectUtils.isNotEmpty(price)
                || ObjectUtils.isNotEmpty(duration);
    }
}
