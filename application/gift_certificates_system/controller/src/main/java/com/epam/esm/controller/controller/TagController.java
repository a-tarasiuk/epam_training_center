package com.epam.esm.controller.controller;

import com.epam.esm.controller.util.hateoas.LinkBuilder;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.pojo.MostWidelyUsedTag;
import com.epam.esm.model.util.MessagePropertyKey;
import com.epam.esm.model.util.UrlMapping;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
 * Working with the tag PROJECT layer.
 *
 * @see com.epam.esm.service.impl.GiftCertificateServiceImpl
 */
@RestController
@RequestMapping(value = UrlMapping.TAGS)
@Validated
public class TagController {
    private final TagServiceImpl service;
    private final LinkBuilder<TagDto> linkBuilder;

    @Autowired
    public TagController(TagServiceImpl service, LinkBuilder<TagDto> linkBuilder) {
        this.service = service;
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
        TagDto tag = service.create(tagDto);
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
        TagDto tag = service.findById(id);
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
    public Page<TagDto> findAll(@Valid EsmPagination pagination) {
        return service.findAll(pagination)
                .map(linkBuilder::build);
    }

    /**
     * Delete tag by it is ID.
     *
     * @param id Tag ID.
     */
    @DeleteMapping(UrlMapping.ID)
    public ResponseEntity<Void> delete(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                                       @PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Find most widely used tag.
     *
     * @return Set of MostWidelyUsedTag.
     */
    @GetMapping(UrlMapping.MOST_WIDELY_USED_TAG_OF_TOP_USER)
    public CollectionModel<MostWidelyUsedTag> findMostWidelyUsedTagOfTopUser() {
        Set<MostWidelyUsedTag> tags = service.findMostWidelyUsedTagsOfTopUser();
        return CollectionModel.of(tags);
    }
}
