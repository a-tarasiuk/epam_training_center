package com.epam.esm.model.entity;

import com.epam.esm.model.listener.GiftCertificateToTagRelationListener;
import com.epam.esm.model.util.DatabaseColumnName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


/**
 * Gift certificate to tag relation entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@EntityListeners(GiftCertificateToTagRelationListener.class)
public class GiftCertificateToTagRelation implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = DatabaseColumnName.GIFT_CERTIFICATE_ID)
    private GiftCertificate giftCertificate;

    @Id
    @ManyToOne
    @JoinColumn(name = DatabaseColumnName.TAG_ID)
    private Tag tag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateToTagRelation that = (GiftCertificateToTagRelation) o;
        return giftCertificate.equals(that.giftCertificate) && tag.equals(that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giftCertificate, tag);
    }
}
