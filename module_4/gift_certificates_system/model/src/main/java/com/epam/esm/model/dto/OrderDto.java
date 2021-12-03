package com.epam.esm.model.dto;

import com.epam.esm.model.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

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