package com.epam.esm.dto;

import com.epam.esm.util.DtoRegexValidator;
import com.epam.esm.util.MessagePropertyKey;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class TagDto extends RepresentationModel<TagDto> {
    private Long id;

    @NotBlank(message = MessagePropertyKey.VALIDATION_TAG_NAME_NOT_EMPTY)
    @Pattern(regexp = DtoRegexValidator.TAG_NAME,
            flags = Pattern.Flag.UNICODE_CASE,
            message = MessagePropertyKey.VALIDATION_TAG_NAME)
    private String name;
}