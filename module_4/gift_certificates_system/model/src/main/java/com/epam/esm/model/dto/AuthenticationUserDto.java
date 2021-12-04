package com.epam.esm.model.dto;

import com.epam.esm.model.util.DtoRegexValidator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.epam.esm.model.util.MessagePropertyKey.VALIDATION_USER_LOGIN;
import static com.epam.esm.model.util.MessagePropertyKey.VALIDATION_USER_LOGIN_NOT_EMPTY;
import static com.epam.esm.model.util.MessagePropertyKey.VALIDATION_USER_PASSWORD_NOT_EMPTY;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class AuthenticationUserDto extends AbstractDto<AuthenticationUserDto> {
    @NotBlank(message = VALIDATION_USER_LOGIN_NOT_EMPTY)
    @Pattern(regexp = DtoRegexValidator.USER_LOGIN,
            flags = Pattern.Flag.UNICODE_CASE,
            message = VALIDATION_USER_LOGIN)
    private String login;

    @NotBlank(message = VALIDATION_USER_PASSWORD_NOT_EMPTY)
    private String password;
}