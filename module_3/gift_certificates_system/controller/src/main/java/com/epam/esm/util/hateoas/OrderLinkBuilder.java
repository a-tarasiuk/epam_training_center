package com.epam.esm.util.hateoas;

import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.EsmPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
