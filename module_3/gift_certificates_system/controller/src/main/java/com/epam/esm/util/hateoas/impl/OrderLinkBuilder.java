package com.epam.esm.util.hateoas.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class OrderLinkBuilder extends AbstractLinkBuilder<OrderDto> {
    private final UserLinkBuilder userLinkBuilder;
    private final GiftCertificateLinkBuilder gcLinkBuilder;

    @Autowired
    public OrderLinkBuilder(UserLinkBuilder userLinkBuilder, GiftCertificateLinkBuilder gcLinkBuilder) {
        this.userLinkBuilder = userLinkBuilder;
        this.gcLinkBuilder = gcLinkBuilder;
    }

    @Override
    public void build(OrderDto dto) {
        userLinkBuilder.build(dto.getUser());
        gcLinkBuilder.build(dto.getGiftCertificate());
        buildSelf(OrderController.class, dto);
    }

    @Override
    public void build(Set<OrderDto> dtos) {
        dtos.forEach(this::build);
    }
}
