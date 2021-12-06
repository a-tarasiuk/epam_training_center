package com.epam.esm.model.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class OrderShortInformationDto extends AbstractDto<OrderShortInformationDto> {
    private BigDecimal price;
    private LocalDateTime purchaseDate;
}