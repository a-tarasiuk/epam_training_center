package com.epam.esm.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class GiftCertificateToTagRelationId implements Serializable {
    private Long giftCertificate;
    private Long tag;
}
