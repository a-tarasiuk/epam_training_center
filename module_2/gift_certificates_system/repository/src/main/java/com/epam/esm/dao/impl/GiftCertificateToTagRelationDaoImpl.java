package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateToTagRelationDao;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.util.SqlQueryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Gift certificate to tag relation DAO implementation.
 */
@Component
public class GiftCertificateToTagRelationDaoImpl extends GiftCertificateToTagRelationDao<GiftCertificateToTagRelation> {
    private static final String CREATE_RELATION_SQL = "INSERT INTO gift_certificate_to_tag_relation (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String DELETE_RELATION_BY_ID_SQL = "DELETE FROM gift_certificate_to_tag_relation WHERE gift_certificate_id = ?";

    private final JdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new Tag dao.
     *
     * @param jdbcTemplate - Jdbc template interface.
     */
    @Autowired
    public GiftCertificateToTagRelationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(long giftCertificateId, long tagId) {
        int countCreatedRows = jdbcTemplate.update(CREATE_RELATION_SQL, giftCertificateId, tagId);
        return countCreatedRows == SqlQueryStatus.SUCCESSFUL;
    }

    @Override
    public boolean deleteAllTagsByGiftCertificateId(long giftCertificateId) {
        int countDeletedRows = jdbcTemplate.update(DELETE_RELATION_BY_ID_SQL, giftCertificateId);
        return countDeletedRows >= SqlQueryStatus.SUCCESSFUL;
    }
}
