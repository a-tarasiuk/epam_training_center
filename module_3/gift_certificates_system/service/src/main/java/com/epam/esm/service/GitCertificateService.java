package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateUpdateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.pagination.EsmPagination;

import java.util.Set;

/**
 * Gift certificate SERVICE layer.
 */
public interface GitCertificateService extends AbstractService<GiftCertificateDto> {
    /**
     * Update gift certificate by it is ID and gift certificate entity.
     *
     * @param id              - Gift certificate ID.
     * @param gcUpdateDto - Gift certificate DTO.
     * @return - Updated gift certificate
     */
    GiftCertificateDto update(long id, GiftCertificateUpdateDto gcUpdateDto);

    Set<GiftCertificateDto> findAll(EsmPagination esmPagination, Set<String> sortBy);

    Set<GiftCertificateDto> findByTagName(String tagName);

    /**
     * Finding list of gift certificates by tag name.
     *
     * @param names - Tag names.
     * @return - Set of gift certificates.
     */
    Set<GiftCertificateDto> findByTagNames(Set<String> names);

    /**
     * Finding list of gift certificates by keyword.
     *
     * @param keyword - Keyword.
     * @return - Set of gift certificates.
     */
    Set<GiftCertificateDto> findByKeyword(String keyword);
}
