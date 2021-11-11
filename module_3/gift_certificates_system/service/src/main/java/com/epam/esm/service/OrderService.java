package com.epam.esm.service;

import com.epam.esm.dto.OrderCreateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.util.pagination.EsmPagination;

import java.util.Set;

/**
 * Order service layer.
 */
public interface OrderService extends AbstractService<OrderDto> {
    /**
     * Create order DTO.
     *
     * @param orderCreateDto Order DTO entity.
     * @return Created order DTO.
     */
    OrderDto create(OrderCreateDto orderCreateDto);

    /**
     * Find all orders DTO by user ID.
     *
     * @param userId        User ID.
     * @param esmPagination Pagination entity.
     * @return Set of found users.
     */
    Set<OrderDto> findAllByUserId(long userId, EsmPagination esmPagination);
}
