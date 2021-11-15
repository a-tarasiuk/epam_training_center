package com.epam.esm.util.hateoas;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.util.EsmPagination;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserLinkBuilder extends AbstractLinkBuilder<UserDto> {
    @Override
    public void build(UserDto dto) {
        buildSelf(UserController.class, dto);
        dto.add(linkTo(methodOn(UserController.class).findAllOrdersByUserId(dto.getId(), new EsmPagination()))
                .withRel("findAllOrdersByUserId").withType(HttpMethod.GET.name()));
    }

    @Override
    public void build(Set<UserDto> dtos) {
        dtos.forEach(this::build);
    }
}
