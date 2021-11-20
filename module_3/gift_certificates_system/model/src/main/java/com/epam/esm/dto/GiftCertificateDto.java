package com.epam.esm.dto;

import com.epam.esm.util.DtoRegexValidator;
import com.epam.esm.util.MessagePropertyKey;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCertificateDto extends AbstractDto<GiftCertificateDto> {
    @NotBlank(message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_NAME_NOT_EMPTY)
    @Pattern(regexp = DtoRegexValidator.GIFT_CERTIFICATE_NAME,
            flags = Pattern.Flag.UNICODE_CASE,
            message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_NAME)
    private String name;

    @NotBlank(message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_DESCRIPTION_NOT_EMPTY)
    @Pattern(regexp = DtoRegexValidator.GIFT_CERTIFICATE_DESCRIPTION,
            flags = Pattern.Flag.UNICODE_CASE,
            message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_DESCRIPTION)
    private String description;

    @NotNull(message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_PRICE_NOT_NULL)
    @DecimalMin(value = "0.0",
            inclusive = false,
            message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_PRICE_MINIMAL)
    @Digits(integer = 10,
            fraction = 2,
            message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_PRICE)
    private BigDecimal price;

    @NotNull(message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_DURATION_NOT_NULL)
    @Range(min = 1, max = 365,
            message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_DURATION)
    private Integer duration;

    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    @NotEmpty(message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_TAGS_NOT_EMPTY)
    private Set<@Valid TagDto> tags;
}
