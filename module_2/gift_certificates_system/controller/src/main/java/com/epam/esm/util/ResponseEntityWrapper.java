package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Response entity wrapper.
 *
 * @see com.epam.esm.util.ResponseEntityWrapper
 */
public class ResponseEntityWrapper {

    /**
     * Response entity wrapper for gift certificate.
     *
     * @param giftCertificate - Entity of the gift certificate.
     * @return - Response Entity.
     */
    public static ResponseEntity<GiftCertificate> wrap(GiftCertificate giftCertificate) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath(UrlMapping.GIFT_CERTIFICATES)
                .path(UrlMapping.ID)
                .buildAndExpand(giftCertificate.getId())
                .toUri();

        return ResponseEntity.created(uri).body(giftCertificate);
    }

    /**
     * Response entity wrapper for tag.
     *
     * @param tag - Entity of the tag.
     * @return - Response Entity.
     */
    public static ResponseEntity<Tag> wrap(Tag tag) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath(UrlMapping.TAGS)
                .path(UrlMapping.ID)
                .buildAndExpand(tag.getId())
                .toUri();

        return ResponseEntity.created(uri).body(tag);
    }
}
