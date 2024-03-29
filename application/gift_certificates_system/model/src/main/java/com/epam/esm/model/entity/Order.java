package com.epam.esm.model.entity;

import com.epam.esm.model.listener.OrderListener;
import com.epam.esm.model.util.DatabaseTableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.epam.esm.model.util.DatabaseColumnName.GIFT_CERTIFICATE_ID;
import static com.epam.esm.model.util.DatabaseColumnName.USER_ID;

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
    @JoinColumn(name = USER_ID)
    private User user;

    @ManyToOne
    @JoinColumn(name = GIFT_CERTIFICATE_ID)
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