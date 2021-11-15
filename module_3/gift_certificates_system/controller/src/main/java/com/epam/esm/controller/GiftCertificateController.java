package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateUpdateDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.GitCertificateService;
import com.epam.esm.util.EsmPagination;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.ParameterName;
import com.epam.esm.util.UrlMapping;
import com.epam.esm.util.hateoas.LinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Gift certificate controller.
 * Working with the gift certificate SERVICE layer.
 */
@RestController
@RequestMapping(value = UrlMapping.GIFT_CERTIFICATES)
@Validated
public class GiftCertificateController {
    private final GitCertificateService service;
    private final LinkBuilder<GiftCertificateDto> linkBuilder;

    @Autowired
    public GiftCertificateController(GitCertificateService service, LinkBuilder<GiftCertificateDto> linkBuilder) {
        this.service = service;
        this.linkBuilder = linkBuilder;
    }

    /**
     * Create gift certificate entity.
     *
     * @param gcDto - Gift certificate DTO.
     * @return - Extension of HttpEntity that adds an HttpStatus status code.
     * Used in RestTemplate as well as in @Controller methods.
     */
    @PostMapping
    public EntityModel<GiftCertificateDto> create(@Valid @RequestBody GiftCertificateDto gcDto) {
        GiftCertificateDto gc = service.create(gcDto);
        linkBuilder.build(gc);
        return EntityModel.of(gc);
    }

    /**
     * Find all gift certificates DTO.
     *
     * @param esmPagination Pagination parameters.
     * @return Set of found gift certificates DTO.
     */
    @GetMapping
    public CollectionModel<GiftCertificateDto> findAll(@Valid EsmPagination esmPagination) {
        Set<GiftCertificateDto> gcs = service.findAll(esmPagination);
        linkBuilder.build(gcs);
        return CollectionModel.of(gcs);
    }

    /**
     * Find all gift certificates DTO with sort parameters and pagination.
     *
     * @param sortBy        Set of gift certificate field names.
     * @param esmPagination Pagination parameters.
     * @return Set of found gift certificates DTO.
     */
    @GetMapping(params = ParameterName.SORT_BY)
    public CollectionModel<GiftCertificateDto> findAllSortByOrderBy(@RequestParam(value = ParameterName.SORT_BY) Set<String> sortBy,
                                                                    @Valid EsmPagination esmPagination) {
        Set<GiftCertificateDto> gcs = service.findAll(esmPagination, sortBy);
        linkBuilder.build(gcs);
        return CollectionModel.of(gcs);
    }

    /**
     * Finding gift certificate by it is ID.
     *
     * @param id Gift certificate ID.
     * @return Entity of the gift certificate.
     */
    @GetMapping(path = UrlMapping.ID)
    public EntityModel<GiftCertificateDto> findById(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                                                    @PathVariable long id) {
        GiftCertificateDto gc = service.findById(id);
        linkBuilder.build(gc);
        return EntityModel.of(gc);
    }

    /**
     * Finding gift certificate by tag name(s).
     *
     * @param tagNames Tag name(s).
     * @return List of gift certificates.
     */
    @GetMapping(params = ParameterName.TAG_NAME)
    public EntityModel<GiftCertificateDto> findByTagNames(@RequestParam(name = ParameterName.TAG_NAME)
                                                                  Set<@NotBlank(message = MessagePropertyKey.VALIDATION_TAG_NAME_NOT_EMPTY) String> tagNames) {
        GiftCertificateDto gc = service.findByTagNames(tagNames);
        linkBuilder.build(gc);
        return EntityModel.of(gc);
    }

    /**
     * Finding gift certificate by keyword.
     *
     * @param keyword Keyword.
     * @return List of gift certificates.
     */
    @GetMapping(params = ParameterName.KEYWORD)
    public CollectionModel<GiftCertificateDto> findByKeyword(@RequestParam(name = ParameterName.KEYWORD)
                                                             @NotBlank(message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_KEYWORD_NOT_BLANK)
                                                                     String keyword) {
        Set<GiftCertificateDto> gcs = service.findByKeyword(keyword);
        linkBuilder.build(gcs);
        return CollectionModel.of(gcs);
    }

    /**
     * Update gift certificate by it is ID.
     *
     * @param id          Gift certificate ID.
     * @param gcUpdateDto Gift certificate DTO.
     */
    @PatchMapping(path = UrlMapping.ID)
    public EntityModel<GiftCertificateDto> update(@PathVariable long id,
                                                  @Valid @RequestBody GiftCertificateUpdateDto gcUpdateDto) {
        GiftCertificateDto gc = service.update(id, gcUpdateDto);
        linkBuilder.build(gc);
        return EntityModel.of(gc);
    }

    /**
     * Delete gift certificate by it is ID.
     *
     * @param id Gift certificate ID.
     */
    @DeleteMapping(path = UrlMapping.ID)
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
