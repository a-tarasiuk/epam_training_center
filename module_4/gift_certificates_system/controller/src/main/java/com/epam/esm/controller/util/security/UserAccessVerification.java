package com.epam.esm.controller.util.security;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.epam.esm.model.entity.User.*;

@Component
@Log4j2
public class UserAccessVerification {
    private final UserService userService;

    public UserAccessVerification(UserService userService) {
        this.userService = userService;
    }

    public boolean isVerifiedById(long userId) {
        UserDto user = userService.findById(userId);
        User authorizationUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String loginFromRequest = user.getLogin();
        String loginFromContext = authorizationUser.getLogin();

        return StringUtils.equals(loginFromRequest, loginFromContext)
                || StringUtils.equals(authorizationUser.getRole().name(), Role.ROLE_ADMINISTRATOR.name());
    }
}
