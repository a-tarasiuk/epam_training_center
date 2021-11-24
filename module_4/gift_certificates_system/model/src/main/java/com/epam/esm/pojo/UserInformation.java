package com.epam.esm.pojo;

import com.epam.esm.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Contain user with the highest cost of all orders and sum of all orders for this user.
 *
 * @see com.epam.esm.pojo.MostWidelyUsedTag
 */
@Data
@AllArgsConstructor
public class UserInformation {
    private User user;
    private BigDecimal sumOfAllOrders;
}
