package com.epam.esm.util.hateoas.impl;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.util.hateoas.LinkBuilder;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public abstract class AbstractLinkBuilder<T extends AbstractDto<T>> implements LinkBuilder<T> {
    protected void buildSelf(Class<?> controller, T dto) {
        Link self = linkTo(controller).slash(dto.getId()).withSelfRel();
        dto.add(self);
    }
}
