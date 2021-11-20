package com.epam.esm.pojo;

import com.epam.esm.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Contain most widely used tag for user with the highest cost of all orders.<br>
 * Also contain set of tag and the number of times these tags have been used by the user.
 *
 * @see UserInformation
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class MostWidelyUsedTag {
    private Long numberOfUses;
    private Set<Tag> tags;
    private UserInformation userInformation;

    /**
     * Set number of times these tags have been used by the user.
     *
     * @param numberOfUses count of uses.
     * @return this object with number of uses tags.
     */
    public MostWidelyUsedTag setNumberOfUsesTags(Long numberOfUses) {
        this.numberOfUses = numberOfUses;
        return this;
    }

    /**
     * Set of most widely used tag.
     *
     * @param tags set of tag.
     * @return this object with set of tag.
     */
    public MostWidelyUsedTag setTags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Set UserInformation.
     *
     * @param userInformation user price.
     * @return this object.
     */
    public MostWidelyUsedTag setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
        return this;
    }
}
