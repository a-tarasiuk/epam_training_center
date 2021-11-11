package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.UrlMapping;
import com.epam.esm.util.pagination.EsmPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
@RequestMapping(value = UrlMapping.ORDERS, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class OrderController {
    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    /**
     * Find order by ID.
     *
     * @param id Order ID.
     * @return Order DTO.
     */
    @GetMapping(UrlMapping.ID)
    public EntityModel<OrderDto> findOrderById(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                                               @PathVariable long id) {
        OrderDto order = orderService.findById(id);

        return EntityModel.of(order,
                linkTo(OrderController.class).slash(order.getId()).withSelfRel(),
                linkTo(methodOn(OrderController.class).findAllOrders(new EsmPagination()))
                        .withRel("findAllOrders").withType(HttpMethod.GET.name())
        );
    }

    /**
     * Find all orders.
     *
     * @param esmPagination Pagination parameters.
     * @return Set of found orders DTO.
     */
    @GetMapping
    public CollectionModel<OrderDto> findAllOrders(@Valid EsmPagination esmPagination) {
        Set<OrderDto> orders = orderService.findAll(esmPagination);

        for (OrderDto orderDto : orders) {
            long id = orderDto.getId();
            Link selfLink = linkTo(OrderController.class).slash(id).withSelfRel();
            Link findOrderById = linkTo(methodOn(OrderController.class).findOrderById(id))
                    .withRel("findOrderById").withType(HttpMethod.GET.name());
            orderDto.add(selfLink).add(findOrderById);
        }

        return CollectionModel.of(orders, linkTo(OrderController.class).withSelfRel());
    }
}