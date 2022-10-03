package com.epam.esm.model.dto;

import com.epam.esm.model.util.DtoRegexValidator;
import com.epam.esm.model.util.MessagePropertyKey;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class TagDto extends AbstractDto<TagDto> {
    @NotBlank(message = MessagePropertyKey.VALIDATION_TAG_NAME_NOT_EMPTY)
    @Pattern(regexp = DtoRegexValidator.TAG_NAME,
            flags = Pattern.Flag.UNICODE_CASE,
            message = MessagePropertyKey.VALIDATION_TAG_NAME)
    private String name;
}