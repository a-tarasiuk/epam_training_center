package com.epam.esm.model.listener;

import com.epam.esm.model.entity.User;

import javax.persistence.PrePersist;

import static com.epam.esm.model.entity.User.Role.ROLE_USER;

/**
 * Why by default Role for new User set to {@code ROLE_USER}?<br>
 * Because the application requirements contain requirement:<br>
 * - Administrator (can be added only via database call)
 */
public final class UserListener {
    @PrePersist
    public void beforeCreate(User user) {
        user.setRole(ROLE_USER);
    }
}
