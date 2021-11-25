package com.epam.esm.repository.util;

import com.epam.esm.model.entity.GiftCertificateToTagRelation;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.pojo.GiftCertificateSearchParameter;
import com.epam.esm.model.util.ColumnName;
import com.epam.esm.model.util.DatabaseColumnName;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_ESM_SORT_DIRECTION_INCORRECT_VALUE;

public final class CriteriaQueryGenerator<T> implements Serializable {
    private static final String DEFAULT_SORT = "id.asc";
    private static final String DELIMITER = "\\.";
    private static final int COUNT_PARTS_IN_PARAMETER = 2;
    private static final String ONE_DELIMITER_REGEX = "^[\\w]*\\.[\\w]*$";

    private CriteriaBuilder builder;
    private CriteriaQuery<T> criteriaQuery;
    private Root<GiftCertificateToTagRelation> root;
    private Path<T> certificate;

    private CriteriaQueryGenerator() {
    }

    public CriteriaQueryGenerator(EntityManager em, Class<T> entity) {
        builder = em.getCriteriaBuilder();
        criteriaQuery = builder.createQuery(entity);
        root = criteriaQuery.from(GiftCertificateToTagRelation.class);
        certificate = root.get(ParameterName.GIFT_CERTIFICATE);
    }

    private static boolean isParameterContainDelimiter(String parameter) {
        return Pattern.matches(ONE_DELIMITER_REGEX, parameter);
    }

    public CriteriaQuery<T> generate(GiftCertificateSearchParameter searchParameter) {
        String keyword = searchParameter.getKeyword();
        if (ObjectUtils.isNotEmpty(keyword)) {
            generateForKeyword(keyword);
        }

        Set<String> tagNames = searchParameter.getTagNames();
        if (ObjectUtils.isNotEmpty(tagNames)) {
            generateForTagNames(tagNames);
        }

        List<Order> orders = generateForFields(searchParameter.getSortBy());
        return criteriaQuery.select(root.get(ParameterName.GIFT_CERTIFICATE))
                .orderBy(orders);
    }

    private void generateForKeyword(String keyword) {
        String keywordLike = SqlGenerator.like(keyword);
        Predicate nameLike = builder.like(certificate.get(DatabaseColumnName.NAME), keywordLike);
        Predicate descriptionLike = builder.like(certificate.get(DatabaseColumnName.DESCRIPTION), keywordLike);
        Predicate result = builder.or(nameLike, descriptionLike);

        criteriaQuery.where(result);
    }

    /**
     * JPA query:<br>
     * SELECT gc FROM GiftCertificate gc JOIN GiftCertificateToTagRelation relation on gc = relation.giftCertificate WHERE relation.tag IN (:tags) GROUP BY gc HAVING COUNT(gc) = :countTags
     */
    private void generateForTagNames(Set<String> tagNames) {
        Join<GiftCertificateToTagRelation, Tag> rel = root.join(ParameterName.TAG);
        Predicate condition = rel.get(ParameterName.NAME).in(tagNames);

        criteriaQuery.where(condition)
                .groupBy(certificate)
                .having(builder.count(certificate).in(tagNames.size()));
    }

    private List<Order> generateForFields(Set<String> sortBy) {
        Set<String> sort = ObjectUtils.isEmpty(sortBy) ? Collections.singleton(DEFAULT_SORT) : sortBy;
        return findOrders(sort);
    }

    private List<Order> findOrders(Set<String> sort) {
        List<Order> orders = new ArrayList<>();

        for (String value : sort) {
            Order order;

            if (isParameterContainDelimiter(value)) {
                String[] parse = value.split(DELIMITER, COUNT_PARTS_IN_PARAMETER);
                String parseColumnName = parse[0];
                String parseDirection = parse[1];

                ColumnName columnName = ColumnName.convertFromString(parseColumnName);
                Direction direction = Direction.convertFromString(parseDirection);

                order = defineOrder(columnName, direction);
            } else {
                ColumnName columnName = ColumnName.convertFromString(value);
                order = defineOrder(columnName);
            }

            orders.add(order);
        }

        return orders;
    }

    private Order defineOrder(ColumnName columnName) {
        String name = columnName.name().toLowerCase();
        Path<String> path = certificate.get(name);
        return builder.asc(path);
    }

    private Order defineOrder(ColumnName columnName, Direction direction) {
        String name = columnName.name().toLowerCase();
        Path<String> path = certificate.get(name);
        return direction == Direction.DESC ? builder.desc(path) : builder.asc(path);
    }

    private enum Direction {
        ASC,
        DESC;

        Direction() {
        }

        public static CriteriaQueryGenerator.Direction convertFromString(String value) {
            try {
                return valueOf(value.toUpperCase(Locale.US));
            } catch (Exception e) {
                throw new IllegalArgumentException(EXCEPTION_ESM_SORT_DIRECTION_INCORRECT_VALUE);
            }
        }
    }
}
