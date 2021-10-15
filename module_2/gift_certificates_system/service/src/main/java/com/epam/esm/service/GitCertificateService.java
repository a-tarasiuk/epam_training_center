package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SqlSortOperator;

import java.util.List;
import java.util.Set;

/**
 * Gift certificate SERVICE layer.
 */
public interface GitCertificateService extends AbstractService<GiftCertificate> {
    /**
     * Update gift certificate by it is ID and gift certificate entity.
     *
     * @param id              - Gift certificate ID.
     * @param giftCertificate - Entity of the gift certificate.
     * @return - Operation result (true or false)
     */
    boolean update(long id, GiftCertificate giftCertificate);

    /**
     * Search for a list of gift certificates and sort them by column name.
     *
     * @return - List of sorted gift certificates.
     * @see com.epam.esm.util.ColumnName
     * @see com.epam.esm.util.SqlSortOperator
     */
    List<GiftCertificate> findAllSorted(Set<ColumnName> columnNames);

    /**
     * Search for a list of gift certificates and sort them by column name and sort ASC\DESC.
     *
     * @param columnNames     - Column names.
     * @param sqlSortOperator - ASC, DESC.
     * @return - List of found gift certificates.
     */
    List<GiftCertificate> findAllSortedWithType(Set<ColumnName> columnNames, SqlSortOperator sqlSortOperator);

    /**
     * Finding list of gift certificates by tag name.
     *
     * @param tagName - Tag name.
     * @return - List of gift certificates.
     */
    List<GiftCertificate> findByTagName(String tagName);

    /**
     * Finding list of gift certificates by part of name.
     *
     * @param partOfName - Part of gift certificate name.
     * @return - List of gift certificates.
     */
    List<GiftCertificate> findByPartOfName(String partOfName);

    /**
     * Finding list of gift certificates by part of description.
     *
     * @param partOfDescription - Part of gift certificate description.
     * @return - List of gift certificates.
     */
    List<GiftCertificate> findByPartOfDescription(String partOfDescription);

    /**
     * Finding list of gift certificates by keyword.
     *
     * @param keyword - Keyword.
     * @return - List of gift certificates.
     */
    List<GiftCertificate> findByKeyword(String keyword);
}
