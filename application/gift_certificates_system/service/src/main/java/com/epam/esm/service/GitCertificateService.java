package com.epam.esm.service;

<<<<<<< HEAD
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SqlSortOperator;

import java.util.List;
import java.util.Set;
=======
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.GiftCertificateUpdateDto;
import com.epam.esm.model.pojo.GiftCertificateSearchParameter;
import com.epam.esm.repository.util.EsmPagination;
import org.springframework.data.domain.Page;
>>>>>>> module_6

/**
 * Gift certificate SERVICE layer.
 */
<<<<<<< HEAD
public interface GitCertificateService extends AbstractService<GiftCertificate> {
    /**
     * Update gift certificate by it is ID and gift certificate entity.
     *
     * @param id              - Gift certificate ID.
     * @param giftCertificate - Entity of the gift certificate.
     * @return - Updated gift certificate
     */
    GiftCertificate update(long id, GiftCertificate giftCertificate);

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
=======
public interface GitCertificateService extends CreateService<GiftCertificateDto> {
    /**
     * Update gift certificate by it is ID and gift certificate entity.
     *
     * @param id                       - Gift certificate ID.
     * @param giftCertificateUpdateDto - Gift certificate DTO.
     * @return - Updated gift certificate
     */
    GiftCertificateDto update(long id, GiftCertificateUpdateDto giftCertificateUpdateDto);

    /**
     * Find all gift certificates with tags by parameters.
     *
     * @param pagination      pagination.
     * @param searchParameter search parameters for gift certificate(s).
     * @return Set of found gift certificates.
     */
    Page<GiftCertificateDto> findAll(EsmPagination pagination, GiftCertificateSearchParameter searchParameter);
>>>>>>> module_6
}
