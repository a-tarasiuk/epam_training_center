package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateUpdateDto;
import com.epam.esm.util.EsmPagination;

import java.util.Set;

/**
 * Gift certificate SERVICE layer.
 */
public interface GitCertificateService extends CreateService<GiftCertificateDto> {
    /**
     * Update gift certificate by it is ID and gift certificate entity.
     *
     * @param id          - Gift certificate ID.
     * @param gcUpdateDto - Gift certificate DTO.
     * @return - Updated gift certificate
     */
    GiftCertificateDto update(long id, GiftCertificateUpdateDto gcUpdateDto);

    /**
     * Find all gift certificates DTO with sorting.
     *
     * @param esmPagination Pagination entity.
     * @param sortBy        Sorting parameters.
     * @return Set of gift certificate DTO.
     */
    Set<GiftCertificateDto> findAll(EsmPagination esmPagination, Set<String> sortBy);

    /**
     * Finding list of gift certificates by tag name.
     *
     * @param names Tag names.
     * @return Set of found gift certificates.
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
