package com.epam.esm.repository;

import com.epam.esm.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Page<Order> findAllByUserId(long id, Pageable pageable);
}
