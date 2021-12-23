package com.epam.esm.controller.util.hateoas.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.model.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserLinkBuilder extends AbstractLinkBuilder<UserDto> {
    @Override
    public UserDto build(UserDto user) {
        return buildSelf(UserController.class, user);
    }

    @Override
    public Set<UserDto> build(Set<UserDto> users) {
        return users.stream()
                .map(this::build)
                .collect(Collectors.toSet());
    }
}
