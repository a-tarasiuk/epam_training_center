package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.UrlMapping;
import com.epam.esm.util.pagination.EsmPagination;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.Set;

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
     * @param tagService Tag service implementation.
     */
    @Autowired
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    /**
     * Create tag entity.
     *
     * @param tagDto Tag DTO.
     * @return Tag DTO with HATEOAS.
     */
    @PostMapping
    public EntityModel<TagDto> create(@Valid @RequestBody TagDto tagDto) {
        TagDto tag = tagService.create(tagDto);

        Link delete = linkTo(methodOn(TagController.class).delete(tag.getId())).withRel("delete").withType(HttpMethod.GET.name());
        return EntityModel.of(tag, delete);
    }

    /**
     * Find tag by it is ID.
     *
     * @param id Tag ID.
     * @return Tag DTO with HATEOAS.
     */
    @GetMapping(UrlMapping.ID)
    public EntityModel<TagDto> findTagById(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                                           @PathVariable long id) {
        TagDto tag = tagService.findById(id);

        Link delete = linkTo(methodOn(TagController.class).delete(tag.getId())).withRel("delete").withType(HttpMethod.GET.name());
        return EntityModel.of(tag, delete);
    }

    /**
     * Find all tags.
     *
     * @param pagination Pagination parameters.
     * @return Set of tags DTO with HATEOAS.
     */
    @GetMapping
    public CollectionModel<TagDto> findAll(@Valid EsmPagination pagination) {
        Set<TagDto> tags = tagService.findAll(pagination);

        for (TagDto tag : tags) {
            Link self = linkTo(methodOn(TagController.class).findTagById(tag.getId())).withRel("findTagById").withType(HttpMethod.GET.name());
            Link delete = linkTo(methodOn(TagController.class).delete(tag.getId())).withRel("delete").withType(HttpMethod.GET.name());
            tag.add(self).add(delete);
        }

        Link currentMethod = linkTo(methodOn(TagController.class).findAll(new EsmPagination())).withSelfRel();
        return CollectionModel.of(tags, currentMethod);
    }

    /**
     * Delete tag by it is ID.
     *
     * @param id Tag ID.
     */
    @DeleteMapping(UrlMapping.ID)
    public ResponseEntity<Void> delete(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                                       @PathVariable long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Find most widely used tags.
     *
     * @return Map with tag and user.
     */
    @GetMapping(UrlMapping.MOST_WIDELY_USED_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS)
    public Map<Tag, User> findMostWidelyUsedTags() {
        return tagService.findMostWidelyUsedTags();
    }
}
