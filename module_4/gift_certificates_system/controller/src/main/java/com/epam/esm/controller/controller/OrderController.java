package com.epam.esm.controller.controller;

import com.epam.esm.controller.util.hateoas.LinkBuilder;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.util.MessagePropertyKey;
import com.epam.esm.model.util.UrlMapping;
import com.epam.esm.model.view.View;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.epam.esm.model.util.MessagePropertyKey.*;

@RestController
@RequestMapping(value = UrlMapping.ORDERS)
@Validated
public class OrderController {
    private final OrderServiceImpl service;
    private final LinkBuilder<OrderDto> linkBuilder;

    @Autowired
    public OrderController(OrderServiceImpl service, LinkBuilder<OrderDto> linkBuilder) {
        this.service = service;
        this.linkBuilder = linkBuilder;
    }

    /**
     * Create order for user by user ID.
     *
     * @param userId            user ID.
     * @param giftCertificateId gift certificate ID.
     * @return Created order DTO.
     */
    @PostMapping(UrlMapping.CREATE_ORDER_FOR_USER)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@userAccessVerification.verifyAuthorizationUser(#userId)")
    public EntityModel<OrderDto> createOrderForUser(@PathVariable
                                                    @NotNull(message = VALIDATION_USER_ID_NOT_EMPTY)
                                                    @Min(value = 1, message = VALIDATION_USER_ID) Long userId,
                                                    @NotNull(message = VALIDATION_GIFT_CERTIFICATE_ID_NOT_NULL)
                                                    @Min(value = 1, message = VALIDATION_GIFT_CERTIFICATE_ID)
                                                    @RequestBody Long giftCertificateId) {
        OrderDto order = service.create(userId, giftCertificateId);
        return EntityModel.of(linkBuilder.build(order));
    }

    /**
     * Find order by ID.
     *
     * @param id Order ID.
     * @return Order DTO.
     */
    @GetMapping(UrlMapping.ID)
    @ResponseStatus(HttpStatus.FOUND)
    public EntityModel<OrderDto> findById(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                                          @PathVariable long id) {
        OrderDto order = service.findById(id);
        linkBuilder.build(order);
        return EntityModel.of(order);
    }

    /**
     * Find all orders.
     *
     * @param pagination Pagination parameters.
     * @return Set of found orders DTO.
     */
    @GetMapping(UrlMapping.ORDERS)
    @ResponseStatus(HttpStatus.FOUND)
    public Page<OrderDto> findAll(@Valid EsmPagination pagination) {
        return service.findAll(pagination);
    }

    /**
     * Find all orders by user ID.
     *
     * @param userId     User ID.
     * @param pagination Pagination parameters.
     * @return Set of found order DTO.
     */
    @GetMapping(UrlMapping.CREATE_ORDER_FOR_USER)
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("@userAccessVerification.verifyLoggedUser(#userId)")
    public Page<OrderDto> findAllOrdersByUserId(@Min(value = 1, message = VALIDATION_USER_ID)
                                                @PathVariable long userId,
                                                @Valid EsmPagination pagination) {
        return service.findAllByUserId(userId, pagination);
    }

    /**
     * Find order by ID for User by ID.
     *
     * @param userId  user ID.
     * @param orderId order ID.
     * @return Entity model of found order.
     */
    @GetMapping(UrlMapping.FIND_ORDER_FOR_USER)
    @JsonView(View.FindOrderForUser.class)
    @ResponseStatus(HttpStatus.FOUND)
    public OrderDto findOrderForUser(@Min(value = 1, message = VALIDATION_USER_ID)
                                     @PathVariable long userId,
                                     @Min(value = 1, message = VALIDATION_ORDER_ID)
                                     @PathVariable long orderId) {
        OrderDto order = service.findOrderForUser(orderId, userId);
        return linkBuilder.build(order);
    }
}