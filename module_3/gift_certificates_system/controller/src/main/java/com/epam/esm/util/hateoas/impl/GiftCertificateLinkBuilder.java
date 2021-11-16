package com.epam.esm.util.hateoas.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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
    public GiftCertificateDto build(GiftCertificateDto dto) {
        buildSelf(GiftCertificateController.class, dto);
        tagLinkBuilder.build(dto.getTags());
        return dto.add(linkTo(methodOn(GiftCertificateController.class).delete(dto.getId()))
                        .withRel(MethodName.DELETE.name()).withType(HttpMethod.DELETE.name()),
                linkTo(methodOn(GiftCertificateController.class).update(dto.getId(), new GiftCertificateUpdateDto()))
                        .withRel(MethodName.UPDATE.name()).withType(HttpMethod.DELETE.name()));
    }

    @Override
    public Set<GiftCertificateDto> build(Set<GiftCertificateDto> dtos) {
        return dtos.stream()
                .map(this::build)
                .collect(Collectors.toSet());
    }
}
