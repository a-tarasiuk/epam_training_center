package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.ResponseEntityWrapper;
import com.epam.esm.util.UrlMapping;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<TagDto> create(@Valid @RequestBody TagDto tagDto) {
        TagDto createdTag = tagService.create(tagDto);
        return ResponseEntityWrapper.wrap(createdTag);
    }

    /**
     * Find tag by it is ID.
     *
     * @param id - Tag ID.
     * @return - Entity of the tag.
     */
    @GetMapping(UrlMapping.ID)
    public TagDto findById(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                           @PathVariable long id) {
        return tagService.findById(id);
    }

    /**
     * Finding all tags.
     *
     * @return - List of the tags.
     */
    @GetMapping
    public Set<TagDto> findAll() {
        return tagService.findAll();
    }

    /**
     * Delete tag by it is ID.
     *
     * @param id - Tag ID.
     */
    @DeleteMapping(UrlMapping.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                       @PathVariable long id) {
        tagService.delete(id);
    }
}
