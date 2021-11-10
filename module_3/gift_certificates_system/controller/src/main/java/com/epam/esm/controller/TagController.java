package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.UrlMapping;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Tag controller.
 * Working with the tag SERVICE layer.
 *
 * @see com.epam.esm.service.impl.GiftCertificateServiceImpl
 */
@Log4j2
@RestController
@RequestMapping(value = UrlMapping.TAGS, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class TagController {
    private final TagServiceImpl tagService;

    /**
     * Initialize of the tag service implementation.
     *
     * @param tagService - Tag service implementation.
     */
    @Autowired
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    /**
     * Create tag entity.
     *
     * @param tagDto - Tag DTO.
     * @return - Extension of HttpEntity that adds an HttpStatus status code.
     * Used in RestTemplate as well as in @Controller methods.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<TagDto> create(@Valid @RequestBody TagDto tagDto) {
        TagDto tag = tagService.create(tagDto);

        return EntityModel.of(tag,
                linkTo(TagController.class).slash(tag.getId()).withSelfRel(),
                linkTo(methodOn(TagController.class).findTagById(tag.getId()))
                        .withRel("findTagById").withType(HttpMethod.GET.name()),
                linkTo(methodOn(TagController.class).findMostWidelyUsedTags())
                        .withRel("findMostWidelyUsedTags").withType(HttpMethod.GET.name())
                );
    }

    /**
     * Find tag by it is ID.
     *
     * @param id - Tag ID.
     * @return - Entity of the tag.
     */
    @GetMapping(UrlMapping.ID)
    public EntityModel<TagDto> findTagById(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                              @PathVariable long id) {
        TagDto tag = tagService.findById(id);
        return EntityModel.of(tag,
                linkTo(TagController.class).slash(tag.getId()).withSelfRel(),
                linkTo(methodOn(TagController.class).findMostWidelyUsedTags())
                        .withRel("findMostWidelyUsedTags").withType(HttpMethod.GET.name())
                );
    }

    /**
     * Delete tag by it is ID.
     *
     * @param id Tag ID.
     */
    @DeleteMapping(UrlMapping.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                       @PathVariable long id) {
        tagService.delete(id);
    }

    @GetMapping(UrlMapping.MOST_WIDELY_USED_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS)
    public Map<Tag, User> findMostWidelyUsedTags() {
        return tagService.findMostWidelyUsedTags();
    }
}
