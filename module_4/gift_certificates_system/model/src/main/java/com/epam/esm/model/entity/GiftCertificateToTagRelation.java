package com.epam.esm.model.entity;

import com.epam.esm.model.listener.GiftCertificateToTagRelationListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

import static com.epam.esm.model.util.DatabaseColumnName.GIFT_CERTIFICATE_ID;
import static com.epam.esm.model.util.DatabaseColumnName.TAG_ID;


/**
 * Gift certificate to tag relation entity.
 *
 * @see com.epam.esm.model.listener.GiftCertificateToTagRelationListener
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@EntityListeners(GiftCertificateToTagRelationListener.class)
@IdClass(GiftCertificateToTagRelationId.class)
public class GiftCertificateToTagRelation implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = GIFT_CERTIFICATE_ID)
    private GiftCertificate giftCertificate;

    @Id
    @ManyToOne
    @JoinColumn(name = TAG_ID)
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
