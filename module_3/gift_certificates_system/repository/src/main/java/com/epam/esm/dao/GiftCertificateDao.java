package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.util.EsmPagination;

import java.util.List;
import java.util.Set;

/**
 * Gift certificate DAO layer class.
 * Works with database.
 */
public abstract class GiftCertificateDao extends AbstractDao<GiftCertificate> {
    /**
     * Updating fields of gift certificate.
     *
     * @param giftCertificate Gift certificate.
     * @return Updated gift certificate.
     */
    public abstract GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Find set of gift certificates with paginate and sorting by field(s) by DESC\ASC.
     *
     * @param esmPagination Contain number of page and count elements on page.
     * @param sortBy        Set of gift certificate fields name.
     * @return Set of gift certificate.
     */
    public abstract Set<GiftCertificate> findAll(EsmPagination esmPagination, Set<String> sortBy);

    /**
     * Finding list of gift certificates by keyword.
     *
     * @param keyword Keyword.
     * @return List of found gift certificates.
     */
    public abstract Set<GiftCertificate> findBy(String keyword);

    /**
     * Finding set of gift certificates by tag ID.
     *
     * @param tag Tag entity.
     * @return List of found gift certificates.
     */
    public abstract Set<GiftCertificate> findBy(Tag tag);

    /**
     * Finding set of gift certificates by set of tags.
     *
     * @param tags Set of tags.
     * @return Set of found gift certificates.
     */
    public abstract Set<GiftCertificate> findBy(Set<Tag> tags);

    /**
     * Finding set of gift certificates by User entity.
     *
     * @param user User entity.
     * @return List of found gift certificates.
     */
    public abstract List<GiftCertificate> findBy(User user);
}
