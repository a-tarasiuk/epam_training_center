package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
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

    /**
     * Response entity wrapper for tag.
     *
     * @param userDto - User DTO.
     * @return - Response Entity.
     */
    public static ResponseEntity<UserDto> wrap(UserDto userDto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath(UrlMapping.USERS)
                .path(UrlMapping.ID)
                .buildAndExpand(userDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    /**
     * Response entity wrapper for tag.
     *
     * @param orderDto - Order DTO.
     * @return - Response Entity.
     */
    public static ResponseEntity<OrderDto> wrap(OrderDto orderDto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath(UrlMapping.ORDERS)
                .path(UrlMapping.ID)
                .buildAndExpand(orderDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(orderDto);
    }
}
