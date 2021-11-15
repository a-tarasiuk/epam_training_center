package com.epam.esm.service;

import com.epam.esm.entity.MostWidelyUsedTag;

import java.util.Set;

public interface TagService<T> extends AbstractService<T> {
    /**
     * Finds the most used tags with the user with the highest value of all orders.
     *
     * @return set of MostWidelyUsedTag.
     * @see com.epam.esm.entity.MostWidelyUsedTag
     */
    public Set<MostWidelyUsedTag> findMostWidelyUsedTags();
}
