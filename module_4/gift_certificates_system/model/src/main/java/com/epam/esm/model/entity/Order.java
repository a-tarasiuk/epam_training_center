package com.epam.esm.model.entity;

import com.epam.esm.model.listener.OrderListener;
import com.epam.esm.model.util.DatabaseColumnName;
import com.epam.esm.model.util.DatabaseTableName;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Order entity.
 *
 * @see com.epam.esm.model.listener.OrderListener
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(OrderListener.class)
@ToString
@Table(name = DatabaseTableName.ORDER)
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = DatabaseColumnName.USER_ID)
    private User user;

    @ManyToOne
    @JoinColumn(name = DatabaseColumnName.GIFT_CERTIFICATE_ID)
    private GiftCertificate giftCertificate;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime purchaseDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getUser().equals(order.getUser())
                && getGiftCertificate().equals(order.getGiftCertificate())
                && getPrice().equals(order.getPrice())
                && getPurchaseDate().equals(order.getPurchaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getGiftCertificate(), getPrice(), getPurchaseDate());
    }
}