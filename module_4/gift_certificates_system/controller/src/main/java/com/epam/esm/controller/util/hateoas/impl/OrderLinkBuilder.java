package com.epam.esm.controller.util.hateoas.impl;

import com.epam.esm.controller.controller.OrderController;
import com.epam.esm.model.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderLinkBuilder extends AbstractLinkBuilder<OrderDto> {
    private final UserLinkBuilder userLinkBuilder;
    private final GiftCertificateLinkBuilder gcLinkBuilder;

    @Autowired
    public OrderLinkBuilder(UserLinkBuilder userLinkBuilder, GiftCertificateLinkBuilder certificateLinkBuilder) {
        this.userLinkBuilder = userLinkBuilder;
        this.gcLinkBuilder = certificateLinkBuilder;
    }

    @Override
    public OrderDto build(OrderDto order) {
        userLinkBuilder.build(order.getUser());
        gcLinkBuilder.build(order.getGiftCertificate());
        return buildSelf(OrderController.class, order);
    }

    @Override
    public Set<OrderDto> build(Set<OrderDto> orders) {
        return orders.stream()
                .map(this::build)
                .collect(Collectors.toSet());
    }
}
