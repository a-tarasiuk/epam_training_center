package com.epam.esm.model.listener;

import com.epam.esm.model.entity.Order;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public final class OrderListener {
    @PrePersist
    public void beforeCreate(Order order) {
        order.setPurchaseDate(LocalDateTime.now());
    }
}
