package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.UrlMapping;
import com.epam.esm.util.hateoas.LinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import static com.epam.esm.util.MessagePropertyKey.VALIDATION_ID;

@RestController
@RequestMapping(value = UrlMapping.USERS)
@Validated
public class UserController {
    private final UserServiceImpl service;
    private final LinkBuilder<UserDto> linkBuilderUser;

    @Autowired
    public UserController(UserServiceImpl service, LinkBuilder<UserDto> linkBuilderUser) {
        this.service = service;
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
        UserDto user = service.create(userDto);
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
        UserDto user = service.findById(id);
        linkBuilderUser.build(user);
        return EntityModel.of(user);
    }

    /**
     * Find all users.
     *
     * @param pagination Pagination parameters.
     * @return Set of found user DTO.
     */
    @GetMapping
    public Page<UserDto> findAll(@Valid EsmPagination pagination) {
        return service.findAll(pagination);
    }
}