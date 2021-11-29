package com.epam.esm.service;

import com.epam.esm.model.pojo.MostWidelyUsedTag;

import java.util.Set;

public interface TagService<T> extends CreateService<T> {
    /**
     * Finds the most used tags with the user with the highest value of all orders.
     *
     * @return set of MostWidelyUsedTag.
     * @see com.epam.esm.model.pojo.MostWidelyUsedTag
     */
    public Set<MostWidelyUsedTag> findMostWidelyUsedTags();
}
