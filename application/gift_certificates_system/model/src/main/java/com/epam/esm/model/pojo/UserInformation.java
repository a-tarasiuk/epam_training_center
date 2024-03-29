package com.epam.esm.model.pojo;

import com.epam.esm.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Contain user with the highest cost of all orders and sum of all orders for this user.
 *
 * @see com.epam.esm.model.pojo.MostWidelyUsedTag
 */
@Data
@AllArgsConstructor
public class UserInformation {
    private User user;
    private BigDecimal sumOfAllOrders;
}
