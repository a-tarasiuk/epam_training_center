package com.epam.esm.util.hateoas.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserLinkBuilder extends AbstractLinkBuilder<UserDto> {
    @Override
    public UserDto build(UserDto dto) {
        return buildSelf(UserController.class, dto);
    }

    @Override
    public Set<UserDto> build(Set<UserDto> dtos) {
        return dtos.stream()
                .map(this::build)
                .collect(Collectors.toSet());
    }
}
