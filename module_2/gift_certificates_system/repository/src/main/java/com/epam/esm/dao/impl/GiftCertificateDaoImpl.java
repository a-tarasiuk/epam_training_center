package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SqlQueryGenerator;
import com.epam.esm.util.SqlQueryStatus;
import com.epam.esm.util.SqlSortOperator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Gift certificate DAO implementation.
 */
@Log4j2
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String CREATE_GIFT_CERTIFICATE_SQL
            = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_GIFT_CERTIFICATE_SQL = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String UPDATE_GIFT_CERTIFICATE_BY_ID_SQL
            = "UPDATE gift_certificate SET name = ?, description = ?, price = ?, duration = ?, last_update_date = ? WHERE id = ?";
    private static final String FIND_GIFT_CERTIFICATE_BY_ID_SQL
            = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate WHERE id = ?";
    private static final String FIND_GIFT_CERTIFICATE_BY_NAME_SQL
            = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate WHERE name = ?";
    private static final String FIND_GIFT_CERTIFICATES_BY_PART_OF_NAME_SQL
            = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate WHERE name LIKE ?";
    private static final String FIND_GIFT_CERTIFICATES_BY_PART_OF_DESCRIPTION_SQL
            = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate WHERE description LIKE ?";
    private static final String FIND_GIFT_CERTIFICATES_BY_KEYWORD_SQL
            = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate WHERE name OR description LIKE ?";
    private static final String FIND_GIFT_CERTIFICATES_BY_TAG_ID_SQL
            = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date FROM gift_certificate AS gc INNER JOIN gift_certificate_to_tag_relation AS relation ON relation.gift_certificate_id = gc.id WHERE relation.tag_id = ?";

    private final JdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new Tag dao.
     *
     * @param jdbcTemplate - Jdbc template interface.
     */
    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificate create(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_GIFT_CERTIFICATE_SQL, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setFloat(3, entity.getPrice());
            preparedStatement.setInt(4, entity.getDuration());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(entity.getCreateDate()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(entity.getLastUpdateDate()));

            return preparedStatement;
        }, keyHolder);

        long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        entity.setId(generatedId);

        return entity;
    }

    @Override
    public boolean delete(long id) {
        int countDeletedRows = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_SQL, id);
        return countDeletedRows == SqlQueryStatus.SUCCESSFUL;
    }

    @Override
    public GiftCertificate update(long id, GiftCertificate giftCertificate) {
        LocalDateTime lastUpdateTime = giftCertificate.getLastUpdateDate();

        jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_BY_ID_SQL,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                Timestamp.valueOf(lastUpdateTime),
                giftCertificate.getId());

        giftCertificate.setId(id);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftCertificateList;
        String sqlQuery = SqlQueryGenerator.findAll();

        try {
            giftCertificateList = jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(GiftCertificate.class));
        } catch (DataAccessException e) {
            giftCertificateList = new ArrayList<>();

            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }

        return giftCertificateList;
    }

    @Override
    public List<GiftCertificate> findAllSorted(Set<ColumnName> orderBy) {
        List<GiftCertificate> giftCertificateList;
        String sqlQuery = SqlQueryGenerator.findAllSorted(orderBy);

        try {
            giftCertificateList = jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(GiftCertificate.class));
        } catch (DataAccessException e) {
            giftCertificateList = new ArrayList<>();

            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }

        return giftCertificateList;
    }

    @Override
    public List<GiftCertificate> findAllSorted(Set<ColumnName> orderBy, SqlSortOperator sqlSortOperator) {
        List<GiftCertificate> giftCertificateList;
        String sqlQuery = SqlQueryGenerator.findAllSorted(orderBy, sqlSortOperator);

        try {
            giftCertificateList = jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(GiftCertificate.class));
        } catch (DataAccessException e) {
            giftCertificateList = new ArrayList<>();

            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }

        return giftCertificateList;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        Optional<GiftCertificate> optionalGiftCertificate;

        try {
            optionalGiftCertificate = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_GIFT_CERTIFICATE_BY_ID_SQL,
                    new BeanPropertyRowMapper<>(GiftCertificate.class), id));
        } catch (DataAccessException e) {
            optionalGiftCertificate = Optional.empty();

            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }

        return optionalGiftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        Optional<GiftCertificate> optionalGiftCertificate;

        try {
            optionalGiftCertificate = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_GIFT_CERTIFICATE_BY_NAME_SQL,
                    new BeanPropertyRowMapper<>(GiftCertificate.class), name));
        } catch (DataAccessException e) {
            optionalGiftCertificate = Optional.empty();

            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }

        return optionalGiftCertificate;
    }

    @Override
    public List<GiftCertificate> findByPartOfName(String partOfName) {
        String valueForSqlQuery = SqlQueryGenerator.forLikeOperator(partOfName);
        return jdbcTemplate.query(FIND_GIFT_CERTIFICATES_BY_PART_OF_NAME_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class), valueForSqlQuery);
    }

    @Override
    public List<GiftCertificate> findByPartOfDescription(String partOfDescription) {
        String valueForSqlQuery = SqlQueryGenerator.forLikeOperator(partOfDescription);
        return jdbcTemplate.query(FIND_GIFT_CERTIFICATES_BY_PART_OF_DESCRIPTION_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class), valueForSqlQuery);
    }

    @Override
    public List<GiftCertificate> findByKeyword(String keyword) {
        String valueForSqlQuery = SqlQueryGenerator.forLikeOperator(keyword);
        return jdbcTemplate.query(FIND_GIFT_CERTIFICATES_BY_KEYWORD_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class), valueForSqlQuery);
    }

    @Override
    public List<GiftCertificate> findByTagId(long id) {
        return jdbcTemplate.query(FIND_GIFT_CERTIFICATES_BY_TAG_ID_SQL, new BeanPropertyRowMapper<>(GiftCertificate.class), id);
    }
}
