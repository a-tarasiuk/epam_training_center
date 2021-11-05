package com.epam.esm.util;

import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import static com.epam.esm.util.MessagePropertyKey.EXCEPTION_ESM_SORT_DIRECTION_INCORRECT_VALUE;

public final class CriteriaQueryGenerator<T> implements Serializable {
    private static final String DEFAULT_SORT = "id.asc";
    private static final String DELIMITER = "\\.";
    private static final int COUNT_PARTS_IN_PARAMETER = 2;
    private static final String ONE_DELIMITER_REGEX = "^[\\w]*\\.[\\w]*$";

    private CriteriaBuilder cb;
    private CriteriaQuery<T> cq;
    private Root<T> from;

    private CriteriaQueryGenerator() {
    }

    public CriteriaQueryGenerator(EntityManager em, Class<T> entity) {
        cb = em.getCriteriaBuilder();
        cq = cb.createQuery(entity);
        from = cq.from(entity);
    }

    public CriteriaQuery<T> generate(Set<String> sortBy) {
        Set<String> sort = ObjectUtils.isEmpty(sortBy) ? Collections.singleton(DEFAULT_SORT) : sortBy;
        List<Order> orders = findOrders(sort);
        return cq.orderBy(orders);
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

    private static boolean isParameterContainDelimiter(String parameter) {
        return Pattern.matches(ONE_DELIMITER_REGEX, parameter);
    }

    private Order defineOrder(ColumnName columnName) {
        String name = columnName.name().toLowerCase();
        Path<String> path = from.get(name);
        return cb.asc(path);
    }

    private Order defineOrder(ColumnName columnName, Direction direction) {
        String name = columnName.name().toLowerCase();
        Path<String> path = from.get(name);
        return direction == Direction.DESC ? cb.desc(path) : cb.asc(path);
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
