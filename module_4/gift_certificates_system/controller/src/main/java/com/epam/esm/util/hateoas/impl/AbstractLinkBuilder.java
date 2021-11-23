package com.epam.esm.util.hateoas.impl;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.util.hateoas.LinkBuilder;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public abstract class AbstractLinkBuilder<T extends AbstractDto<T>> implements LinkBuilder<T> {
    protected T buildSelf(Class<?> controller, T dto) {
        Link self = linkTo(controller).slash(dto.getId()).withSelfRel();
        return dto.add(self);
    }

    protected enum MethodName {
        DELETE("delete"),
        UPDATE("update"),
        FIND_ALL_ORDERS_BY_USER_ID("findAllOrdersByUserId");

        MethodName(String name) {
        }
    }
}
