package com.epam.esm.link;

import com.epam.esm.dto.AbstractDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EsmLinkBuilder<C, E extends AbstractDto<E>> {
    public CollectionModel<E> build(Class<C> controller, Set<E> set, long id) {
        set.forEach(e -> buildSelf(controller, e, e.getId()));
        return CollectionModel.of(set, buildCurrentLink(controller));
    }

    private void buildSelf(Class<C> controller, E obj, long id) {
        Link selfLink = linkTo(controller).slash(id).withSelfRel();
        obj.add(selfLink);
    }

    private Link buildCurrentLink(Class<C> controller) {
        return linkTo(controller).withSelfRel();
    }
}
