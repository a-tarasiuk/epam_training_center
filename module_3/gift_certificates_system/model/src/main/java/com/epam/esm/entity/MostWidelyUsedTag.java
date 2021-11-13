package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Contain most widely used tag for user with the highest cost of all orders.
 * @see com.epam.esm.entity.UserPrice
 * @see com.epam.esm.entity.TagCount
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class MostWidelyUsedTag {
    private TagCount tagCount;
    private UserPrice userPrice;

    /**
     * Set TagCount.
     *
     * @param tagCount tag count.
     * @return this object.
     */
    public MostWidelyUsedTag setTagCount(TagCount tagCount) {
        this.tagCount = tagCount;
        return this;
    }

    /**
     * Set UserPrice.
     *
     * @param userPrice user price.
     * @return this object.
     */
    public MostWidelyUsedTag setUserPrice(UserPrice userPrice) {
        this.userPrice = userPrice;
        return this;
    }
}
