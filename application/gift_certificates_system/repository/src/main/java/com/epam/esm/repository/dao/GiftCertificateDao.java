package com.epam.esm.repository.dao;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.pojo.GiftCertificateSearchParameter;
import com.epam.esm.repository.util.EsmPagination;

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
     * @param certificate Gift certificate.
     * @return Updated gift certificate.
     */
    public abstract GiftCertificate update(GiftCertificate certificate);

    /**
     * Find gift certificates with:<br>
     * - paginate parameters<br>
     * - sorting by field(s) by DESC\ASC (optional)<br>
     * - by keyword (optional)<br>
     * - by tag names (optional)<br>
     *
     * @param pagination
     * @param parameter
     * @return
     */
    public abstract Set<GiftCertificate> findAll(EsmPagination pagination, GiftCertificateSearchParameter parameter);

    /**
     * Finding set of gift certificates by tag ID.
     *
     * @param tag Tag entity.
     * @return List of found gift certificates.
     */
    public abstract Set<GiftCertificate> findBy(Tag tag);

    /**
     * Finding set of gift certificates by User entity.
     *
     * @param user User entity.
     * @return List of found gift certificates.
     */
    public abstract List<GiftCertificate> findBy(User user);
}
