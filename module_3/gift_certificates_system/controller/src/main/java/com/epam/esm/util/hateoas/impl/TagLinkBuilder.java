package com.epam.esm.util.hateoas.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkBuilder extends AbstractLinkBuilder<TagDto> {
    @Override
    public TagDto build(TagDto dto) {
        buildSelf(TagController.class, dto);
        return dto.add(linkTo(methodOn(TagController.class).delete(dto.getId()))
                .withRel(MethodName.DELETE.name()).withType(HttpMethod.DELETE.name()));
    }

    @Override
    public Set<TagDto> build(Set<TagDto> dtos) {
         return dtos.stream()
                 .map(this::build)
                 .collect(Collectors.toSet());
    }
}
