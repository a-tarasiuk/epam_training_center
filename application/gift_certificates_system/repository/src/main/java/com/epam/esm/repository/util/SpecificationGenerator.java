package com.epam.esm.repository.util;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.GiftCertificate.ColumnName;
import com.epam.esm.model.entity.GiftCertificateToTagRelation;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.pojo.GiftCertificateSearchParameter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
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

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_SORT_CONDITION_INCORRECT_VALUE;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_SORT_DIRECTION_INCORRECT_VALUE;

public final class SpecificationGenerator implements Serializable {
    private static final String DEFAULT_SORT = "id.asc";
    private static final String DELIMITER = "\\.";
    private static final int COUNT_PARTS_IN_PARAMETER = 2;
    private static final String ONE_DELIMITER_REGEX = "^[\\w]*\\.[\\w]*$";

    private SpecificationGenerator() {
    }

    /**
     * SQL query for search by tag names: <br>
     *
     * <code>
     * select *<br>
     * from gift_certificate gc<br>
     * cross join gift_certificate_to_tag_relation relation<br>
     * inner join tag t on relation.tag_id = t.id and (gc.id = relation.gift_certificate_id)<br>
     * where t.name in ('Epam', 'Junior')<br>
     * group by gc.id<br>
     * having count(gc.id) in (2)<br>
     * order by gc.id asc<br>
     * limit ?;<br>
     * </code>
     * <p>
     * JPA query for search by tag names:<br>
     * <code>
     * SELECT gc FROM GiftCertificate gc JOIN GiftCertificateToTagRelation relation on gc = relation.giftCertificate WHERE relation.tag IN (:tags) GROUP BY gc HAVING COUNT(gc) = :countTags
     * </code>
     */
    public static Specification<GiftCertificate> build(final GiftCertificateSearchParameter searchParameter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            String keyword = searchParameter.getKeyword();
            if (ObjectUtils.isNotEmpty(keyword)) {
                String keywordLike = SqlGenerator.like(keyword);
                Predicate nameLike = builder.like(root.get(ColumnName.NAME.name().toLowerCase()), keywordLike);
                Predicate descriptionLike = builder.like(root.get(ColumnName.DESCRIPTION.name().toLowerCase()), keywordLike);
                Predicate condition = builder.or(nameLike, descriptionLike);
                predicates.add(condition);
            }

            Set<String> tagNames = searchParameter.getTagNames();
            if (ObjectUtils.isNotEmpty(tagNames)) {
                Root<GiftCertificateToTagRelation> relation = query.from(GiftCertificateToTagRelation.class);
                Join<GiftCertificateToTagRelation, Tag> rel = relation.join(ParameterName.TAG);
                Predicate on = builder.equal(root, relation.get(ParameterName.GIFT_CERTIFICATE));
                rel.on(on);
                Predicate predicate = rel.get(ParameterName.NAME).in(tagNames);
                predicates.add(predicate);
                query.groupBy(root)
                        .having(builder.count(root).in(tagNames.size()));
            }

            Set<String> sortBy = searchParameter.getSortBy();
            if (ObjectUtils.isNotEmpty(sortBy)) {
                List<Order> orders = generateOrdersForFields(sortBy, builder, root);
                query.orderBy(orders);
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean isParameterContainDelimiter(String parameter) {
        return Pattern.matches(ONE_DELIMITER_REGEX, parameter);
    }

    private static List<Order> generateOrdersForFields(Set<String> sortBy, CriteriaBuilder builder, Path<GiftCertificate> certificate) {
        Set<String> sort = ObjectUtils.isEmpty(sortBy) ? Collections.singleton(DEFAULT_SORT) : sortBy;
        return findOrders(sort, builder, certificate);
    }

    private static List<Order> findOrders(Set<String> sort, CriteriaBuilder builder, Path<GiftCertificate> certificate) {
        List<Order> orders = new ArrayList<>();

        for (String value : sort) {
            Order order;

            if (isParameterContainDelimiter(value)) {
                String[] parse = value.split(DELIMITER, COUNT_PARTS_IN_PARAMETER);
                String parseColumnName = parse[0];
                String parseDirection = parse[1];

                ColumnName columnName = ColumnName.convertFromString(parseColumnName);
                Direction direction = Direction.convertFromString(parseDirection);

                order = defineOrder(columnName, direction, builder, certificate);
            } else {
                ColumnName columnName = ColumnName.convertFromString(value);
                order = defineOrder(columnName, builder, certificate);
            }

            orders.add(order);
        }

        return orders;
    }

    private static Order defineOrder(ColumnName columnName, CriteriaBuilder builder, Path<GiftCertificate> certificate) {
        String name = columnName.name().toLowerCase();
        Path<String> path = certificate.get(name);
        return builder.asc(path);
    }

    private static Order defineOrder(ColumnName columnName, Direction direction, CriteriaBuilder builder, Path<GiftCertificate> certificate) {
        String name = columnName.name().toLowerCase();
        Path<String> path = certificate.get(name);
        return direction == Direction.DESC ? builder.desc(path) : builder.asc(path);
    }

    private enum Condition {
        OR,
        AND;

        Condition() {
        }

        public static Condition convertFromString(String value) {
            try {
                return valueOf(value.toUpperCase(Locale.US));
            } catch (Exception e) {
                throw new IllegalArgumentException(EXCEPTION_SORT_CONDITION_INCORRECT_VALUE);
            }
        }
    }

    private enum Direction {
        ASC,
        DESC;

        Direction() {
        }

        public static Direction convertFromString(String value) {
            try {
                return valueOf(value.toUpperCase(Locale.US));
            } catch (Exception e) {
                throw new IllegalArgumentException(EXCEPTION_SORT_DIRECTION_INCORRECT_VALUE);
            }
        }
    }
}
