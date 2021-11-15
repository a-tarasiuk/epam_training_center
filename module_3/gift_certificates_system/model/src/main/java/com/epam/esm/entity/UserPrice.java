package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Contain user with the highest cost of all orders and sum of all orders for this user.
 *
 * @see com.epam.esm.entity.MostWidelyUsedTag
 */
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserPrice {
    private User user;
    private BigDecimal sumOfAllOrders;
}
