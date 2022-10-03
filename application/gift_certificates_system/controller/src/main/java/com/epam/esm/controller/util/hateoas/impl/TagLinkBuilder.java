package com.epam.esm.controller.util.hateoas.impl;

import com.epam.esm.controller.controller.TagController;
import com.epam.esm.model.dto.TagDto;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkBuilder extends AbstractLinkBuilder<TagDto> {
    @Override
    public TagDto build(TagDto tag) {
        buildSelf(TagController.class, tag);
        return tag.add(linkTo(methodOn(TagController.class).delete(tag.getId()))
                .withRel(MethodName.DELETE.name()).withType(HttpMethod.DELETE.name()));
    }

    @Override
    public Set<TagDto> build(Set<TagDto> tags) {
        return tags.stream()
                .map(this::build)
                .collect(Collectors.toSet());
    }
}
