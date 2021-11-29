package com.epam.esm.service;

import com.epam.esm.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends CreateService<UserDto>, UserDetailsService {
}
