package com.epam.esm.listener;

import com.epam.esm.entity.Order;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public final class OrderListener {
    @PrePersist
    public void beforeCreate(Order order) {
        order.setPurchaseDate(LocalDateTime.now());
    }
}
