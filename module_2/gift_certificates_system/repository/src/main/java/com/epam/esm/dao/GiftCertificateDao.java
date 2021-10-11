package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

/**
 * Gift certificate DAO layer class.
 * Works with database.
 */
public abstract class GiftCertificateDao extends EntityDao<GiftCertificate> {
    /**
     * Create gift certificate to tag relation in the database.
     *
     * @param giftCertificateId - Gift certificate ID.
     * @param tagId             - Tag ID.
     */
    public abstract void createGiftCertificateToTagRelation(long giftCertificateId, long tagId);

    /**
     * Updating fields of gift certificate.
     *
     * @param giftCertificate   - Gift certificate.
     * @return                  - Operation result (Gift certificate updated or not updated)
     */
    public abstract boolean update(GiftCertificate giftCertificate);

    /**
     * Updating fields of gift certificate by gift certificate ID and not null fields.
     *
     * @param id                - Gift certificate ID.
     * @param namedParameters   - Not null fields.
     * @return                  - Operation result (Gift certificate updated or not updated)
     */
    public abstract boolean update(long id, Map<String, Object> namedParameters);

    /**
     * Finding list of gift certificates by part of name.
     *
     * @param partOfName        - Part ot the gift certificate name.
     * @return                  - List of found gift certificates.
     */
    public abstract List<GiftCertificate> findByPartOfName(String partOfName);

    /**
     * Finding list of gift certificates by part of description.
     *
     * @param partOfDescription - Part ot the gift certificate description.
     * @return                  - List of found gift certificates.
     */
    public abstract List<GiftCertificate> findByPartOfDescription(String partOfDescription);

    /**
     * Finding list of gift certificates by keyword.
     *
     * @param keyword           - Keyword.
     * @return                  - List of found gift certificates.
     */
    public abstract List<GiftCertificate> findByKeyword(String keyword);

    /**
     * Finding list of gift certificates by tag ID.
     *
     * @param id                - Tag ID.
     * @return                  - List of found gift certificates.
     */
    public abstract List<GiftCertificate> findByTagId(long id);
}
