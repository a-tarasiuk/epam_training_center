package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateUpdateDto;
import com.epam.esm.dto.OrderCreateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.util.pagination.EsmPagination;

import java.util.Set;

/**
 * Order service layer.
 */
public interface OrderService extends AbstractService<OrderDto> {
    OrderDto create(OrderCreateDto orderCreateDto);

    Set<OrderDto> findAllByUserId(long userId, EsmPagination esmPagination);
}
