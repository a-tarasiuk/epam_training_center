package com.epam.esm.service;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.GiftCertificateUpdateDto;
import com.epam.esm.model.pojo.GiftCertificateSearchParameter;
import com.epam.esm.repository.util.EsmPagination;
import org.springframework.data.domain.Page;

/**
 * Gift certificate SERVICE layer.
 */
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
}
