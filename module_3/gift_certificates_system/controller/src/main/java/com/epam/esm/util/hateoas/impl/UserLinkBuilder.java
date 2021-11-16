package com.epam.esm.util.hateoas.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import com.epam.esm.util.EsmPagination;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserLinkBuilder extends AbstractLinkBuilder<UserDto> {
    @Override
    public UserDto build(UserDto dto) {
        buildSelf(UserController.class, dto);
        return dto.add(linkTo(methodOn(UserController.class).findAllOrdersByUserId(dto.getId(), new EsmPagination()))
                .withRel(MethodName.FIND_ALL_ORDERS_BY_USER_ID.name()).withType(HttpMethod.GET.name()));
    }

    @Override
    public Set<UserDto> build(Set<UserDto> dtos) {
        return dtos.stream()
                .map(this::build)
                .collect(Collectors.toSet());
    }
}
