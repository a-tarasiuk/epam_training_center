package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Contain set of tag and the number of times these tags have been used by the user.
 * @see com.epam.esm.entity.MostWidelyUsedTag
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TagCount {
    private Long numberOfUses;
    private Set<Tag> tags;
}
