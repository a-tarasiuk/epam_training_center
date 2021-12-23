package com.epam.esm.model.pojo;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.Tag;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Contain most widely used tag for user with the highest cost of all orders.<br>
 * Also contain set of tag and the number of times these tags have been used by the user.
 *
 * @see UserInformation
 */
@Builder
@Data
public class MostWidelyUsedTag implements Serializable {
    private UserDto userDto;
    private Long numberOfUses;
    private Set<Tag> tags;
    private BigDecimal sumOfAllOrders;
}