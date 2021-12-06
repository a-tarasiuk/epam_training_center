package com.epam.esm.controller.controller;

import com.epam.esm.controller.util.hateoas.LinkBuilder;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.util.UrlMapping;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static com.epam.esm.model.util.MessagePropertyKey.VALIDATION_ID;

@RestController
@RequestMapping(value = UrlMapping.USERS)
@Validated
public class UserController {
    private final UserService service;
    private final LinkBuilder<UserDto> linkBuilderUser;

    @Autowired
    public UserController(UserServiceImpl service, LinkBuilder<UserDto> linkBuilderUser) {
        this.service = service;
        this.linkBuilderUser = linkBuilderUser;
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