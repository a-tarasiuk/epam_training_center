package com.epam.esm.dto;

import com.epam.esm.util.DtoRegexValidator;
import com.epam.esm.util.MessagePropertyKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.NonNullFields;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class GiftCertificateUpdateDto implements Serializable {
    private Long id;

    @Pattern(regexp = DtoRegexValidator.GIFT_CERTIFICATE_NAME_UPDATE,
            flags = Pattern.Flag.UNICODE_CASE,
            message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_NAME)
    private String name;

    @Pattern(regexp = DtoRegexValidator.GIFT_CERTIFICATE_DESCRIPTION_UPDATE,
            flags = Pattern.Flag.UNICODE_CASE,
            message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_DESCRIPTION)
    private String description;

    @DecimalMin(value = "0.0",
            inclusive = false,
            message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_PRICE_MINIMAL)
    @Digits(integer = 10,
            fraction = 2,
            message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_PRICE)
    private BigDecimal price;

    @Range(min = 1,
            max = 365,
            message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_DURATION)
    private Integer duration;

    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    private Set<@Valid TagDto> tags;
}
