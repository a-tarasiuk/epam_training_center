package com.epam.esm.util.hateoas.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateLinkBuilder extends AbstractLinkBuilder<GiftCertificateDto> {
    private final TagLinkBuilder tagLinkBuilder;

    @Autowired
    public GiftCertificateLinkBuilder(TagLinkBuilder tagLinkBuilder) {
        this.tagLinkBuilder = tagLinkBuilder;
    }

    @Override
    public void build(GiftCertificateDto dto) {
        buildSelf(GiftCertificateController.class, dto);
        tagLinkBuilder.build(dto.getTags());
        dto.add(linkTo(methodOn(GiftCertificateController.class).delete(dto.getId()))
                        .withRel("delete").withType(HttpMethod.GET.name()));
    }

    @Override
    public void build(Set<GiftCertificateDto> dtos) {
        dtos.forEach(this::build);
    }
}
