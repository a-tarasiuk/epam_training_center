package com.epam.esm.listener;

import com.epam.esm.entity.Order;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public final class OrderListener {
    private static LocalDateTime currentDateTime;

    @PrePersist
    public void beforeCreate(Order order) {
        currentDateTime = LocalDateTime.now();
        order.setPurchaseDate(currentDateTime);
    }
}
