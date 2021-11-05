package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
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
     * @param gcCreateDto - Gift certificate DTO.
     * @return - Response Entity.
     */
    public static ResponseEntity<GiftCertificateDto> wrap(GiftCertificateDto gcCreateDto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath(UrlMapping.GIFT_CERTIFICATES)
                .path(UrlMapping.ID)
                .buildAndExpand(gcCreateDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(gcCreateDto);
    }

    /**
     * Response entity wrapper for tag.
     *
     * @param tagDto - Tag DTO.
     * @return - Response Entity.
     */
    public static ResponseEntity<TagDto> wrap(TagDto tagDto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath(UrlMapping.TAGS)
                .path(UrlMapping.ID)
                .buildAndExpand(tagDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(tagDto);
    }
}
