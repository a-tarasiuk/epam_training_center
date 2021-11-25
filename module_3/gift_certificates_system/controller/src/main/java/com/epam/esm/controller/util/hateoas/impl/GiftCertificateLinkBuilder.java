package com.epam.esm.controller.util.hateoas.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.GiftCertificateUpdateDto;
import com.epam.esm.model.dto.TagDto;
import org.apache.commons.lang3.ObjectUtils;
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
    public GiftCertificateDto build(GiftCertificateDto certificate) {
        buildSelf(GiftCertificateController.class, certificate);

        Set<TagDto> tags = certificate.getTags();
        if (ObjectUtils.isNotEmpty(tags)) {
            tagLinkBuilder.build(tags);
        }

        return certificate.add(linkTo(methodOn(GiftCertificateController.class).delete(certificate.getId()))
                        .withRel(MethodName.DELETE.name()).withType(HttpMethod.DELETE.name()),
                linkTo(methodOn(GiftCertificateController.class).update(certificate.getId(), new GiftCertificateUpdateDto()))
                        .withRel(MethodName.UPDATE.name()).withType(HttpMethod.DELETE.name()));
    }

    @Override
    public Set<GiftCertificateDto> build(Set<GiftCertificateDto> certificates) {
        return certificates.stream()
                .map(this::build)
                .collect(Collectors.toSet());
    }
}
