package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortOperator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *  Gift certificate SERVICE layer.
 */
@NoArgsConstructor
@AllArgsConstructor
public abstract class GitCertificateService extends AbstractService<GiftCertificate> {
    /**
     * Instantiates a new gift certificate DAO.
     */
    @Autowired
    protected GiftCertificateDao giftCertificateDao;

    /**
     * Instantiates a new tag DAO.
     */
    @Autowired
    protected TagDao tagDao;

    /**
     * Update gift certificate by it is ID and gift certificate entity.
     *
     * @param id                - Gift certificate ID.
     * @param giftCertificate   - Entity of the gift certificate.
     * @return
     */
    public abstract boolean update(long id, GiftCertificate giftCertificate);

    /**
     * Search for a list of gift certificates and sort them by column name and sort ASC\DESC.
     *
     * @param columnName        - The name of the gift certificate.
     * @param sortOperator      - ASC\DESC.
     * @return                  - List of sorted gift certificates.
     * @see     com.epam.esm.util.ColumnName
     * @see     com.epam.esm.util.SortOperator
     */
    public abstract List<GiftCertificate> findAllAndSort(ColumnName columnName, SortOperator sortOperator);

    /**
     * Finding list of gift certificates by tag name.
     *
     * @param tagName           - Tag name.
     * @return                  - List of gift certificates.
     */
    public abstract List<GiftCertificate> findByTagName(String tagName);

    /**
     * Finding list of gift certificates by part of name.
     *
     * @param partOfName        - Part of gift certificate name.
     * @return                  - List of gift certificates.
     */
    public abstract List<GiftCertificate> findByPartOfName(String partOfName);

    /**
     * Finding list of gift certificates by part of description.
     *
     * @param partOfDescription - Part of gift certificate description.
     * @return                  - List of gift certificates.
     */
    public abstract List<GiftCertificate> findByPartOfDescription(String partOfDescription);

    /**
     * Finding list of gift certificates by keyword.
     *
     * @param keyword           - Keyword.
     * @return                  - List of gift certificates.
     */
    public abstract List<GiftCertificate> findByKeyword(String keyword);
}
