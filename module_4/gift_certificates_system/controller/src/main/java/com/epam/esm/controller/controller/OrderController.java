package com.epam.esm.controller.controller;

import com.epam.esm.controller.util.hateoas.LinkBuilder;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.OrderShortInformationDto;
import com.epam.esm.model.util.MessagePropertyKey;
import com.epam.esm.model.util.UrlMapping;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.epam.esm.model.util.MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_ID;
import static com.epam.esm.model.util.MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_ID_NOT_NULL;
import static com.epam.esm.model.util.MessagePropertyKey.VALIDATION_ORDER_ID;
import static com.epam.esm.model.util.MessagePropertyKey.VALIDATION_USER_ID;
import static com.epam.esm.model.util.MessagePropertyKey.VALIDATION_USER_ID_NOT_EMPTY;

@RestController
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
     * Create order for user by user ID.<br>
     * Authenticated user cannot call this method for other users.
     *
     * @param userId            user ID.
     * @param giftCertificateId gift certificate ID.
     * @return Created order DTO.
     */
    @PostMapping(UrlMapping.ORDER_FOR_USER)
    @PreAuthorize("@userAccessVerification.isVerifiedById(#userId)")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<OrderDto> createByUserIdAndGiftCertificateId(@NotNull(message = VALIDATION_USER_ID_NOT_EMPTY)
                                                                    @Min(value = 1, message = VALIDATION_USER_ID)
                                                                    @PathVariable long userId,
                                                                    @NotNull(message = VALIDATION_GIFT_CERTIFICATE_ID_NOT_NULL)
                                                                    @Min(value = 1, message = VALIDATION_GIFT_CERTIFICATE_ID)
                                                                    @RequestBody long giftCertificateId) {
        OrderDto order = service.create(userId, giftCertificateId);
        linkBuilder.build(order);
        return EntityModel.of(order);
    }

    /**
     * Find order by ID.
     *
     * @param id Order ID.
     * @return Order DTO.
     */
    @GetMapping(UrlMapping.FIND_ORDERS_BY_ID)
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
    public Page<OrderDto> findAll(@Valid EsmPagination pagination) {
        return service.findAll(pagination);
    }

    /**
     * Find all orders by user ID.<br>
     * Authenticated user cannot call this method for other users.
     *
     * @param userId     User ID.
     * @param pagination Pagination parameters.
     * @return Set of found order DTO.
     */
    @GetMapping(UrlMapping.ORDER_FOR_USER)
    @PreAuthorize("@userAccessVerification.isVerifiedById(#userId)")
    public Page<OrderDto> findAllByUserId(@Min(value = 1, message = VALIDATION_USER_ID)
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
    @PreAuthorize("@userAccessVerification.isVerifiedById(#userId)")
    public EntityModel<OrderShortInformationDto> findByUserIdAndOrderId(@Min(value = 1, message = VALIDATION_USER_ID)
                                                                        @PathVariable long userId,
                                                                        @Min(value = 1, message = VALIDATION_ORDER_ID)
                                                                        @PathVariable long orderId) {
        OrderShortInformationDto order = service.findByOrderIdAndUserId(orderId, userId);
        return EntityModel.of(order);
    }
}