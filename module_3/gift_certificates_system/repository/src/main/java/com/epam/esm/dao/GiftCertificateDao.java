package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SqlSortOperator;
import com.epam.esm.util.pagination.EsmPagination;

import javax.persistence.criteria.Order;
import java.util.List;
import java.util.Map;
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
     * @return - Updated gift certificate.
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    Set<GiftCertificate> findAll(EsmPagination esmPagination, Set<String> sortBy);

    /**
     * Finding list of gift certificates by keyword.
     *
     * @param keyword - Keyword.
     * @return - List of found gift certificates.
     */
    Set<GiftCertificate> findByKeyword(String keyword);

    /**
     * Finding list of gift certificates by tag ID.
     *
     * @param tag - Tag entity.
     * @return - List of found gift certificates.
     */
    Set<GiftCertificate> findByTag(Tag tag);
}
