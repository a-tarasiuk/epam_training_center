package com.epam.esm.dto;

import com.epam.esm.util.DtoRegexValidator;
import com.epam.esm.util.MessagePropertyKey;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserDto extends AbstractDto<UserDto> {
    @NotBlank(message = MessagePropertyKey.VALIDATION_USER_LOGIN_NOT_EMPTY)
    @Pattern(regexp = DtoRegexValidator.USER_LOGIN,
            flags = Pattern.Flag.UNICODE_CASE,
            message = MessagePropertyKey.VALIDATION_USER_LOGIN)
    private String login;

    @NotBlank(message = MessagePropertyKey.VALIDATION_USER_FIRST_NAME_NOT_EMPTY)
    @Pattern(regexp = DtoRegexValidator.USER_NAME,
            flags = Pattern.Flag.UNICODE_CASE,
            message = MessagePropertyKey.VALIDATION_USER_FIRST_NAME)
    private String firstName;

    @NotBlank(message = MessagePropertyKey.VALIDATION_USER_LAST_NAME_NOT_EMPTY)
    @Pattern(regexp = DtoRegexValidator.USER_NAME,
            flags = Pattern.Flag.UNICODE_CASE,
            message = MessagePropertyKey.VALIDATION_USER_LAST_NAME)
    private String lastName;
}