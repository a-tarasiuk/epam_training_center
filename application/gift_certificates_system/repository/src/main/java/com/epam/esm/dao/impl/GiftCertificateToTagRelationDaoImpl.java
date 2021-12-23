package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateToTagRelationDao;
import com.epam.esm.entity.GiftCertificateToTagRelation;
import com.epam.esm.util.SqlQueryStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Gift certificate to tag relation DAO implementation.
 */
@Log4j2
@Repository
public class GiftCertificateToTagRelationDaoImpl implements GiftCertificateToTagRelationDao<GiftCertificateToTagRelation> {
    private static final String CREATE_RELATION_SQL = "INSERT INTO gift_certificate_to_tag_relation (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String DELETE_RELATION_SQL = "DELETE FROM gift_certificate_to_tag_relation WHERE gift_certificate_id = ? AND tag_id = ?";
    private static final String FIND_ALL_BY_GIFT_CERTIFICATE_ID_SQL
            = "SELECT gift_certificate_id, tag_id FROM gift_certificate_to_tag_relation WHERE gift_certificate_id = ?";
    private static final String FIND_RELATION_SQL
            = "SELECT gift_certificate_id, tag_id FROM gift_certificate_to_tag_relation WHERE gift_certificate_id = ? AND tag_id = ?";

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
    public boolean delete(long giftCertificateId, long tagId) {
        int countCreatedRows = jdbcTemplate.update(DELETE_RELATION_SQL, giftCertificateId, tagId);
        return countCreatedRows == SqlQueryStatus.SUCCESSFUL;
    }

    @Override
    public Optional<GiftCertificateToTagRelation> find(long giftCertificateId, long tagId) {
        Optional<GiftCertificateToTagRelation> relation;

        try {
            relation = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_RELATION_SQL,
                    new BeanPropertyRowMapper<>(GiftCertificateToTagRelation.class), giftCertificateId, tagId));
        } catch (DataAccessException e) {
            relation = Optional.empty();

            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }

        return relation;
    }

    @Override
    public List<GiftCertificateToTagRelation> findAllByGiftCertificateId(long id) {
        return jdbcTemplate.query(FIND_ALL_BY_GIFT_CERTIFICATE_ID_SQL, new BeanPropertyRowMapper<>(), id);
    }
}