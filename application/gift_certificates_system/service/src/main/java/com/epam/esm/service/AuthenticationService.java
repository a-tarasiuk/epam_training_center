package com.epam.esm.service;

import com.epam.esm.model.dto.AuthenticationUserDto;
import com.epam.esm.model.dto.UserDto;

public interface AuthenticationService {
    UserDto signUp(UserDto userDto);

    UserDto signIn(AuthenticationUserDto authenticationUserDto);
}
