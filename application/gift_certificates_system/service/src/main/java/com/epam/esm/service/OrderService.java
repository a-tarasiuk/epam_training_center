package com.epam.esm.service;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.OrderShortInformationDto;
import com.epam.esm.repository.util.EsmPagination;
import org.springframework.data.domain.Page;

/**
 * Order service layer.
 */
public interface OrderService extends AbstractService<OrderDto> {
    /**
     * Create order DTO.
     *
     * @param userId            user ID.
     * @param giftCertificateId gift certificate ID.
     * @return Created order DTO.
     */
    OrderDto create(long userId, long giftCertificateId);

    /**
     * Find all orders DTO by user ID.
     *
     * @param userId     User ID.
     * @param pagination Pagination entity.
     * @return Set of found users.
     */
    Page<OrderDto> findAllByUserId(long userId, EsmPagination pagination);

    /**
     * Find order by ID for user with ID/
     *
     * @param userId  user ID.
     * @param orderId order ID.
     * @return Order Short Information Dto.
     */
    OrderShortInformationDto findByOrderIdAndUserId(long orderId, long userId);
}
