package com.epam.esm.util.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkBuilder extends AbstractLinkBuilder<TagDto> {
    @Override
    public void build(TagDto dto) {
        buildSelf(TagController.class, dto);
        dto.add(linkTo(methodOn(TagController.class).delete(dto.getId())).withRel("delete").withType(HttpMethod.GET.name()));
    }

    @Override
    public void build(Set<TagDto> dtos) {
        dtos.forEach(this::build);
    }
}
