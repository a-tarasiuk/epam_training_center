package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.UrlMapping;
import com.epam.esm.util.hateoas.LinkBuilder;
import com.epam.esm.view.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
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
import javax.validation.constraints.NotNull;
import java.util.Set;

import static com.epam.esm.util.MessagePropertyKey.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = UrlMapping.USERS)
@Validated
public class UserController {
    private final UserServiceImpl userService;
    private final OrderServiceImpl orderService;
    private final LinkBuilder<UserDto> linkBuilderUser;
    private final LinkBuilder<OrderDto> linkBuilderOrder;

    @Autowired
    public UserController(UserServiceImpl userService, OrderServiceImpl orderService, LinkBuilder<UserDto> linkBuilderUser, LinkBuilder<OrderDto> linkBuilderOrder) {
        this.userService = userService;
        this.orderService = orderService;
        this.linkBuilderUser = linkBuilderUser;
        this.linkBuilderOrder = linkBuilderOrder;
    }

    /**
     * Create user.
     *
     * @param userDto User DTO.
     * @return Created user DTO.
     */
    @PostMapping
    public EntityModel<UserDto> create(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.create(userDto);
        linkBuilderUser.build(user);
        return EntityModel.of(user);
    }

    /**
     * Find user DTO by user ID.
     *
     * @param id User ID.
     * @return User DTO.
     */
    @GetMapping(UrlMapping.ID)
    public EntityModel<UserDto> findById(@Min(value = 1, message = VALIDATION_ID)
                                         @PathVariable long id) {
        UserDto user = userService.findById(id);
        linkBuilderUser.build(user);
        return EntityModel.of(user);
    }

    /**
     * Find all users.
     *
     * @param esmPagination Pagination parameters.
     * @return Set of found user DTO.
     */
    @GetMapping
    public CollectionModel<UserDto> findAll(@Valid EsmPagination esmPagination) {
        Set<UserDto> users = userService.findAll(esmPagination);
        linkBuilderUser.build(users);
        return CollectionModel.of(users);
    }


    /**
     * Create order for user by user ID.
     *
     * @param userId            User ID.
     * @param giftCertificateId gift certificate ID.
     * @return Created order DTO.
     */
    @PostMapping(UrlMapping.ORDERS_USER)
    public EntityModel<OrderDto> createOrderForUser(@Min(value = 1, message = VALIDATION_USER_ID)
                                                    @PathVariable long userId,
                                                    @NotNull(message = VALIDATION_GIFT_CERTIFICATE_ID_NOT_NULL)
                                                    @Min(value = 1, message = VALIDATION_GIFT_CERTIFICATE_ID)
                                                    @RequestBody Long giftCertificateId) {
        OrderDto order = orderService.create(userId, giftCertificateId);

        return EntityModel.of(order,
                linkTo(UserController.class).slash(order.getId()).withSelfRel(),
                linkTo(methodOn(OrderController.class).findAll(new EsmPagination()))
                        .withRel("findAllOrders").withType(HttpMethod.GET.name()),
                linkTo(methodOn(UserController.class).findAllOrdersByUserId(userId, new EsmPagination()))
                        .withRel("findAllOrdersByUserId").withType(HttpMethod.GET.name())
        );
    }


    /**
     * Find all orders by user ID.
     *
     * @param userId        User ID.
     * @param esmPagination Pagination parameters.
     * @return Set of found order DTO.
     */
    @GetMapping(UrlMapping.ORDERS_USER)
    public CollectionModel<OrderDto> findAllOrdersByUserId(@Min(value = 1, message = VALIDATION_USER_ID)
                                                           @PathVariable long userId,
                                                           @Valid EsmPagination esmPagination) {
        Set<OrderDto> orders = orderService.findAllByUserId(userId, esmPagination);

        for (OrderDto orderDto : orders) {
            long id = orderDto.getId();
            Link selfLink = linkTo(UserController.class).slash(id).withSelfRel();
            Link findOrderById = linkTo(methodOn(OrderController.class).findById(id))
                    .withRel("findOrderById").withType(HttpMethod.GET.name());
            orderDto.add(selfLink).add(findOrderById);
        }

        return CollectionModel.of(orders,
                linkTo(UserController.class).withSelfRel(),
                linkTo(methodOn(OrderController.class).findAll(new EsmPagination()))
                        .withRel("findAllOrders").withType(HttpMethod.GET.name())
        );
    }

    /**
     * Find order by ID for User by ID.
     *
     * @param userId user ID.
     * @param orderId order ID.
     * @return Entity model of found order.
     */
    @GetMapping(UrlMapping.ORDER_USER)
    @JsonView(View.FindOrderForUser.class)
    public EntityModel<OrderDto> findOrderForUser(@Min(value = 1, message = VALIDATION_USER_ID)
                                                  @PathVariable long userId,
                                                @Min(value = 1, message = VALIDATION_ORDER_ID)
                                                  @PathVariable long orderId) {
        OrderDto orderDto = orderService.findOrderForUser(orderId, userId);
        return EntityModel.of(linkBuilderOrder.build(orderDto));
    }
}