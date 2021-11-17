package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.pojo.MostWidelyUsedTag;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.UrlMapping;
import com.epam.esm.util.hateoas.LinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Set;

/**
 * Tag controller.
 * Working with the tag SERVICE layer.
 *
 * @see com.epam.esm.service.impl.GiftCertificateServiceImpl
 */
@RestController
@RequestMapping(value = UrlMapping.TAGS)
@Validated
public class TagController {
    private final TagServiceImpl tagService;
    private final LinkBuilder<TagDto> linkBuilder;

    @Autowired
    public TagController(TagServiceImpl tagService, LinkBuilder<TagDto> linkBuilder) {
        this.tagService = tagService;
        this.linkBuilder = linkBuilder;
    }

    /**
     * Create tag entity.
     *
     * @param tagDto Tag DTO.
     * @return Tag DTO with HATEOAS.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<TagDto> create(@Valid @RequestBody TagDto tagDto) {
        TagDto tag = tagService.create(tagDto);
        linkBuilder.build(tag);
        return EntityModel.of(tag);
    }

    /**
     * Find tag by it is ID.
     *
     * @param id Tag ID.
     * @return Tag DTO with HATEOAS.
     */
    @GetMapping(UrlMapping.ID)
    public EntityModel<TagDto> findById(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                                        @PathVariable long id) {
        TagDto tag = tagService.findById(id);
        linkBuilder.build(tag);
        return EntityModel.of(tag);
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
        linkBuilder.build(tags);
        return CollectionModel.of(tags);
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
     * Find most widely used tag.
     *
     * @return Set of MostWidelyUsedTag.
     */
    @GetMapping(UrlMapping.MOST_WIDELY_USED_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS)
    public CollectionModel<MostWidelyUsedTag> findMostWidelyUsedTag() {
        Set<MostWidelyUsedTag> tags = tagService.findMostWidelyUsedTags();
        return CollectionModel.of(tags);
    }
}
