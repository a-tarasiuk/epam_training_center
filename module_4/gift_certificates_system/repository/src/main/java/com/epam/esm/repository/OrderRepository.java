package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Page<Order> findAllByUser(User user, Pageable pageable);
}
