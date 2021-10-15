package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Gift certificate to tag relation entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateToTagRelation {
    private long giftCertificateId;
    private long tagId;
}
