package com.epam.esm.model.entity;

import com.epam.esm.model.listener.GiftCertificateListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Gift certificate entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(GiftCertificateListener.class)
@ToString
public class GiftCertificate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private LocalDateTime lastUpdateDate;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return getName().equals(that.getName())
                && getDescription().equals(that.getDescription())
                && getPrice().equals(that.getPrice())
                && getDuration().equals(that.getDuration())
                && getCreateDate().equals(that.getCreateDate())
                && getLastUpdateDate().equals(that.getLastUpdateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getPrice(), getDuration(), getCreateDate(), getLastUpdateDate());
    }
}