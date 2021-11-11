package com.epam.esm.dto;

import com.epam.esm.util.MessagePropertyKey;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class OrderCreateDto extends RepresentationModel<OrderCreateDto> {
    @Min(value = 1, message = MessagePropertyKey.VALIDATION_USER_ID)
    Long userId;

    @NotNull(message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_ID_NOT_NULL)
    @Min(value = 1, message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_ID)
    Long giftCertificateId;
}