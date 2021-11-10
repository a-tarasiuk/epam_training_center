package com.epam.esm.dto;

import com.epam.esm.util.DtoRegexValidator;
import com.epam.esm.util.MessagePropertyKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class UserDto extends RepresentationModel<UserDto> implements Serializable {
    @Min(value = 1, message = MessagePropertyKey.VALIDATION_USER_ID)
    private Long id;

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