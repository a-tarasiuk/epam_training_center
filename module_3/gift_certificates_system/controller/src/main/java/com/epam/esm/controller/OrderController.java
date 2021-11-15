package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.UrlMapping;
import com.epam.esm.util.hateoas.LinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = UrlMapping.ORDERS)
@Validated
public class OrderController {
    private final OrderServiceImpl orderService;
    private final LinkBuilder<OrderDto> linkBuilder;

    @Autowired
    public OrderController(OrderServiceImpl orderService, LinkBuilder<OrderDto> linkBuilder) {
        this.orderService = orderService;
        this.linkBuilder = linkBuilder;
    }

    /**
     * Find order by ID.
     *
     * @param id Order ID.
     * @return Order DTO.
     */
    @GetMapping(UrlMapping.ID)
    public EntityModel<OrderDto> findById(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                                          @PathVariable long id) {
        OrderDto order = orderService.findById(id);
        linkBuilder.build(order);
        return EntityModel.of(order);
    }

    /**
     * Find all orders.
     *
     * @param esmPagination Pagination parameters.
     * @return Set of found orders DTO.
     */
    @GetMapping
    public CollectionModel<OrderDto> findAll(@Valid EsmPagination esmPagination) {
        Set<OrderDto> orders = orderService.findAll(esmPagination);
        linkBuilder.build(orders);
        return CollectionModel.of(orders);
    }
}