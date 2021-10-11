package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

/**
 * Gift certificate to tag relation entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class GiftCertificateToTagRelation {
    @Positive
    private long giftCertificateId;

    @Positive
    private long tagId;
}
