package com.epam.esm.model.dto;

import com.epam.esm.model.util.DtoRegexValidator;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.epam.esm.model.util.MessagePropertyKey.*;

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