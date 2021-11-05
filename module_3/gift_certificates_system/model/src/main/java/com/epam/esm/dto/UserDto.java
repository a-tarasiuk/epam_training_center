package com.epam.esm.dto;

import com.epam.esm.util.DtoRegexValidator;
import com.epam.esm.util.MessagePropertyKey;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
public class UserDto implements Serializable {

    private Long id;

    @Pattern(regexp = DtoRegexValidator.USER_NAME,
            flags = Pattern.Flag.UNICODE_CASE,
            message = MessagePropertyKey.VALIDATION_USER_FIRST_NAME)
    private String firstName;

    @Pattern(regexp = DtoRegexValidator.USER_NAME,
            flags = Pattern.Flag.UNICODE_CASE,
            message = MessagePropertyKey.VALIDATION_USER_LAST_NAME)
    private String lastName;
}