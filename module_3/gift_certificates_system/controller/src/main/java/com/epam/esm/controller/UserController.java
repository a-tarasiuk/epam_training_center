package com.epam.esm.controller;

import com.epam.esm.dto.OrderCreateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = UrlMapping.USERS, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {
    private final UserServiceImpl userService;
    private final OrderServiceImpl orderService;

    @Autowired
    public UserController(UserServiceImpl userService, OrderServiceImpl orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Operations only for User.
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.create(userDto);

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).findUserById(user.getId()))
                        .withSelfRel(),
                linkTo(methodOn(UserController.class).findAllUsers(new EsmPagination()))
                        .withRel("findAllUsers").withType(HttpMethod.GET.name()),
                linkTo(methodOn(UserController.class).createOrderForUser(user.getId(), new OrderCreateDto()))
                        .withRel("createOrderForUser").withType(HttpMethod.POST.name()),
                linkTo(methodOn(UserController.class).findAllOrdersByUserId(user.getId(), new EsmPagination()))
                        .withRel("createOrderForUser").withType(HttpMethod.GET.name())
        );
    }

    @GetMapping(UrlMapping.ID)
    public EntityModel<UserDto> findUserById(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID) @PathVariable long id) {
        UserDto user = userService.findById(id);

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).createUser(user))
                        .withRel("createUser").withType(HttpMethod.POST.name()),
                linkTo(methodOn(UserController.class).findAllUsers(new EsmPagination()))
                        .withRel("findAllUsers").withType(HttpMethod.GET.name()),
                linkTo(methodOn(UserController.class).createOrderForUser(user.getId(), new OrderCreateDto()))
                        .withRel("createOrderForUser").withType(HttpMethod.POST.name()),
                linkTo(methodOn(UserController.class).findAllOrdersByUserId(user.getId(), new EsmPagination()))
                        .withRel("createOrderForUser").withType(HttpMethod.GET.name())
        );
    }

    @GetMapping
    public CollectionModel<UserDto> findAllUsers(@Valid EsmPagination esmPagination) {
        Set<UserDto> users = userService.findAll(esmPagination);

        for (UserDto user : users) {
            long id = user.getId();
            Link selfLink = linkTo(UserController.class).slash(id).withSelfRel();
            user.add(selfLink);
        }

        return CollectionModel.of(users, linkTo(UserController.class).withSelfRel());
    }

    /**
     * Operations for User with Order.
     */

    // Create order for user by user ID.
    @PostMapping(UrlMapping.ORDER_USER)
    public EntityModel<OrderDto> createOrderForUser(@Min(value = 1, message = MessagePropertyKey.VALIDATION_USER_ID) @PathVariable long userId,
                                                    @Valid @RequestBody OrderCreateDto orderCreateDto) {
        orderCreateDto.setUserId(userId);
        OrderDto order = orderService.create(orderCreateDto);

        return EntityModel.of(order,
                linkTo(UserController.class).withSelfRel(),
                linkTo(methodOn(OrderController.class).findAllOrders(new EsmPagination()))
                        .withRel("findAllOrders").withType(HttpMethod.GET.name()),
                linkTo(methodOn(UserController.class).findAllOrdersByUserId(userId, new EsmPagination()))
                        .withRel("createOrderForUser").withType(HttpMethod.GET.name())
        );
    }

    // Find all orders by user ID.
    @GetMapping(UrlMapping.ORDER_USER)
    public CollectionModel<OrderDto> findAllOrdersByUserId(@Min(value = 1, message = MessagePropertyKey.VALIDATION_USER_ID) @PathVariable long userId,
                                                           @Valid EsmPagination esmPagination) {
        Set<OrderDto> orders = orderService.findAllByUserId(userId, esmPagination);

        for (OrderDto orderDto : orders) {
            long id = orderDto.getId();
            Link selfLink = linkTo(UserController.class).slash(id).withSelfRel();
            Link findOrderById = linkTo(methodOn(OrderController.class).findOrderById(id))
                    .withRel("findOrderById").withType(HttpMethod.GET.name());
            orderDto.add(selfLink).add(findOrderById);
        }

        return CollectionModel.of(orders,
                linkTo(UserController.class).withSelfRel(),
                linkTo(methodOn(UserController.class).createOrderForUser(userId, new OrderCreateDto()))
                        .withRel("createOrderForUser").withType(HttpMethod.GET.name()),
                linkTo(methodOn(OrderController.class).findAllOrders(new EsmPagination()))
                        .withRel("findAllOrders").withType(HttpMethod.GET.name())
        );
    }
}