package com.epam.esm.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
@Setter
public abstract class AbstractDto<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {
    Long id;

    public void buildSelfLink(Class<?> controller) {
        Link self = linkTo(controller).slash(id).withSelfRel();
        this.add(self);
    }
}