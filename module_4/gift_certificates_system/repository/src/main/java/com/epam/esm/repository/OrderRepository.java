package com.epam.esm.repository;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Page<Order> findAllByUser(User user, Pageable pageable);
}
