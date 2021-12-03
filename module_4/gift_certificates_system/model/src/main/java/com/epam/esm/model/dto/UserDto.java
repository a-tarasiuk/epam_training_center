package com.epam.esm.model.dto;

import com.epam.esm.model.util.DtoRegexValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.epam.esm.model.entity.User.Role;
import static com.epam.esm.model.util.MessagePropertyKey.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserDto extends AbstractDto<UserDto> {
    @NotBlank(message = VALIDATION_USER_LOGIN_NOT_EMPTY)
    @Pattern(regexp = DtoRegexValidator.USER_LOGIN,
            flags = Pattern.Flag.UNICODE_CASE,
            message = VALIDATION_USER_LOGIN)
    private String login;

    @NotBlank(message = VALIDATION_USER_FIRST_NAME_NOT_EMPTY)
    @Pattern(regexp = DtoRegexValidator.USER_NAME,
            flags = Pattern.Flag.UNICODE_CASE,
            message = VALIDATION_USER_FIRST_NAME)
    private String firstName;

    @NotBlank(message = VALIDATION_USER_LAST_NAME_NOT_EMPTY)
    @Pattern(regexp = DtoRegexValidator.USER_NAME,
            flags = Pattern.Flag.UNICODE_CASE,
            message = VALIDATION_USER_LAST_NAME)
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = VALIDATION_USER_PASSWORD_NOT_EMPTY)
    private String password;

    private String jwt;
    private Role role;
}