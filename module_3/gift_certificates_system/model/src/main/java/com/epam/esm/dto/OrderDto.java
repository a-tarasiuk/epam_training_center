package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class OrderDto extends AbstractDto<OrderDto> {
    @Valid
    private UserDto user;

    @Valid
    private GiftCertificateDto giftCertificate;

    private BigDecimal price;
    private LocalDateTime purchaseDate;
}