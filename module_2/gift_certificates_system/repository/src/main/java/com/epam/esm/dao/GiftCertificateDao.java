package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SqlSortOperator;

import java.util.List;
import java.util.Set;

/**
 * Gift certificate DAO layer class.
 * Works with database.
 */
public interface GiftCertificateDao extends EntityDao<GiftCertificate> {
    /**
     * Updating fields of gift certificate.
     *
     * @param giftCertificate - Gift certificate.
     * @return - Operation result (Gift certificate updated or not updated)
     */
    boolean update(long id, GiftCertificate giftCertificate);

    /**
     * Finding list of gift certificates and sort by column name from request.
     *
     * @param sortByColumnNames - Column name(s).
     * @return - List of sorted gift certificates.
     */
    List<GiftCertificate> findAllSorted(Set<ColumnName> sortByColumnNames);

    /**
     * Finding list of gift certificates, sort by column name and ASC\DESC from request.
     *
     * @param columnNames     - Column name(s).
     * @param sqlSortOperator - ASC\DESC.
     * @return - List of sorted gift certificates.
     */
    List<GiftCertificate> findAllSorted(Set<ColumnName> columnNames, SqlSortOperator sqlSortOperator);

    /**
     * Finding list of gift certificates by part of name.
     *
     * @param partOfName - Part ot the gift certificate name.
     * @return - List of found gift certificates.
     */
    List<GiftCertificate> findByPartOfName(String partOfName);

    /**
     * Finding list of gift certificates by part of description.
     *
     * @param partOfDescription - Part ot the gift certificate description.
     * @return - List of found gift certificates.
     */
    List<GiftCertificate> findByPartOfDescription(String partOfDescription);

    /**
     * Finding list of gift certificates by keyword.
     *
     * @param keyword - Keyword.
     * @return - List of found gift certificates.
     */
    List<GiftCertificate> findByKeyword(String keyword);

    /**
     * Finding list of gift certificates by tag ID.
     *
     * @param id - Tag ID.
     * @return - List of found gift certificates.
     */
    List<GiftCertificate> findByTagId(long id);
}
