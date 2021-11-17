package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.UrlMapping;
import com.epam.esm.util.hateoas.LinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

import static com.epam.esm.util.MessagePropertyKey.VALIDATION_ID;

@RestController
@RequestMapping(value = UrlMapping.USERS)
@Validated
public class UserController {
    private final UserServiceImpl userService;
    private final LinkBuilder<UserDto> linkBuilderUser;

    @Autowired
    public UserController(UserServiceImpl userService, LinkBuilder<UserDto> linkBuilderUser) {
        this.userService = userService;
        this.linkBuilderUser = linkBuilderUser;
    }

    /**
     * Create user.
     *
     * @param userDto User DTO.
     * @return Created user DTO.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserDto> create(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.create(userDto);
        linkBuilderUser.build(user);
        return EntityModel.of(user);
    }

    /**
     * Find user DTO by user ID.
     *
     * @param id User ID.
     * @return User DTO.
     */
    @GetMapping(UrlMapping.ID)
    public EntityModel<UserDto> findById(@Min(value = 1, message = VALIDATION_ID)
                                         @PathVariable long id) {
        UserDto user = userService.findById(id);
        linkBuilderUser.build(user);
        return EntityModel.of(user);
    }

    /**
     * Find all users.
     *
     * @param esmPagination Pagination parameters.
     * @return Set of found user DTO.
     */
    @GetMapping
    public CollectionModel<UserDto> findAll(@Valid EsmPagination esmPagination) {
        Set<UserDto> users = userService.findAll(esmPagination);
        linkBuilderUser.build(users);
        return CollectionModel.of(users);
    }
}