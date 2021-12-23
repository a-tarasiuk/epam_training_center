package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.SqlQueryStatus;
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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Tag DAO implementation.
 */
@Log4j2
@Repository
public class TagDaoImpl implements TagDao {
    private static final String CREATE_TAG_SQL = "INSERT INTO tag (name) VALUES (?)";
    private static final String DELETE_TAG_BY_ID_SQL = "DELETE FROM tag WHERE id = (?)";
    private static final String FIND_TAG_BY_NAME_SQL = "SELECT id, name FROM tag WHERE name = (?)";
    private static final String FIND_TAG_BY_ID_SQL = "SELECT id, name FROM tag WHERE id = (?)";
    private static final String FIND_ALL_TAGS_SQL = "SELECT id, name FROM tag";
    private static final String FIND_TAGS_BY_CERTIFICATE_ID_SQL
            = "SELECT tag.id, tag.name FROM tag JOIN gift_certificate_to_tag_relation AS relation ON relation.tag_id = tag.id WHERE relation.gift_certificate_id = ?";
    private static final String DELETE_GIFT_CERTIFICATE_TO_TAG_RELATION_BY_ID_SQL = "DELETE FROM gift_certificate_to_tag_relation WHERE gift_certificate_id = ?";

    private final JdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new Tag dao.
     *
     * @param jdbcTemplate - Jdbc template interface.
     */
    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag create(Tag entity) {
        String tagName = entity.getName();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TAG_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tagName);

            return preparedStatement;
        }, keyHolder);

        long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        entity.setId(generatedId);

        return entity;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAGS_SQL, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> findById(long id) {
        Optional<Tag> optionalTag;

        try {
            optionalTag = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_TAG_BY_ID_SQL, new BeanPropertyRowMapper<>(Tag.class), id));
        } catch (DataAccessException e) {
            optionalTag = Optional.empty();

            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }

        return optionalTag;
    }


    @Override
    public Optional<Tag> findByName(String tagName) {
        Optional<Tag> optionalTag;

        try {
            optionalTag = Optional.ofNullable(jdbcTemplate.queryForObject(FIND_TAG_BY_NAME_SQL, new BeanPropertyRowMapper<>(Tag.class), tagName));
        } catch (DataAccessException e) {
            optionalTag = Optional.empty();

            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }

        return optionalTag;
    }

    @Override
    public boolean delete(long id) {
        int countDeletedTags = jdbcTemplate.update(DELETE_TAG_BY_ID_SQL, id);
        return countDeletedTags == SqlQueryStatus.SUCCESSFUL;
    }

    @Override
    public Set<Tag> findByGiftCertificateId(long id) {
        return new HashSet<>(jdbcTemplate.query(FIND_TAGS_BY_CERTIFICATE_ID_SQL, new BeanPropertyRowMapper<>(Tag.class), id));
    }

    @Override
    public boolean deleteAllTagsByGiftCertificateId(long giftCertificateId) {
        int countDeletedRows = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TO_TAG_RELATION_BY_ID_SQL, giftCertificateId);
        return countDeletedRows >= SqlQueryStatus.SUCCESSFUL;
    }
}
