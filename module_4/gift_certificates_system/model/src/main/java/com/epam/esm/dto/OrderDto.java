package com.epam.esm.dto;

import com.epam.esm.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
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

    @JsonView(View.FindOrderForUser.class)
    private BigDecimal price;

    @JsonView(View.FindOrderForUser.class)
    private LocalDateTime purchaseDate;
}