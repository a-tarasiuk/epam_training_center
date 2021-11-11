package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateUpdateDto;
import com.epam.esm.service.GitCertificateService;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.ParameterName;
import com.epam.esm.util.UrlMapping;
import com.epam.esm.util.pagination.EsmPagination;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@RequiredArgsConstructor
@RequestMapping(value = UrlMapping.GIFT_CERTIFICATES, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class GiftCertificateController {
    private final GitCertificateService service;

    /**
     * Create gift certificate entity.
     *
     * @param gcDto - Gift certificate DTO.
     * @return - Extension of HttpEntity that adds an HttpStatus status code.
     * Used in RestTemplate as well as in @Controller methods.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<GiftCertificateDto> create(@Valid @RequestBody GiftCertificateDto gcDto) {
        GiftCertificateDto gc = service.create(gcDto);

        return EntityModel.of(gc,
                linkTo(GiftCertificateController.class).slash(gc.getId()).withSelfRel(),
                linkTo(methodOn(GiftCertificateController.class).findAll(new EsmPagination()))
                        .withRel("findAll").withType(HttpMethod.GET.name()),
                linkTo(methodOn(GiftCertificateController.class).findById(gc.getId()))
                        .withRel("findById").withType(HttpMethod.GET.name())
        );
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

        for (GiftCertificateDto gc : gcs) {
            long id = gc.getId();
            Link selfLink = linkTo(GiftCertificateController.class).slash(id).withSelfRel();
            Link findById = linkTo(methodOn(GiftCertificateController.class).findById(id))
                    .withRel("findById").withType(HttpMethod.GET.name());

            gc.add(selfLink).add(findById);
        }

        Link currentLink = linkTo(GiftCertificateController.class).withSelfRel();

        return CollectionModel.of(gcs, currentLink);
    }

    /**
     * Find all gift certificates DTO with sort parameters and pagination.
     *
     * @param sortBy        Set of gift certificate field names.
     * @param esmPagination Pagination parameters.
     * @param br            Binding result.
     * @return Set of found gift certificates DTO.
     */
    @GetMapping(params = ParameterName.SORT_BY)
    public Set<GiftCertificateDto> findAllSortByOrderBy(@RequestParam(value = ParameterName.SORT_BY) Set<String> sortBy,
                                                        @Valid EsmPagination esmPagination, BindingResult br) {
        return service.findAll(esmPagination, sortBy);
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

        Link currentLink = linkTo(GiftCertificateController.class).slash(gc.getId()).withSelfRel();

        return EntityModel.of(gc, currentLink,
                linkTo(methodOn(GiftCertificateController.class).findAll(new EsmPagination()))
                        .withRel("findAll").withType(HttpMethod.GET.name())
        );
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
        long id = gc.getId();

        Link selfLink = linkTo(GiftCertificateController.class).slash(id).withSelfRel();
        Link findById = linkTo(methodOn(GiftCertificateController.class).findById(id))
                .withRel("findById").withType(HttpMethod.GET.name());

        gc.add(selfLink).add(findById);

        return EntityModel.of(gc);
    }

    /**
     * Finding gift certificate by keyword.
     *
     * @param keyword Keyword.
     * @return List of gift certificates.
     */
    @GetMapping(params = ParameterName.KEYWORD)
    public Set<GiftCertificateDto> findByKeyword(@RequestParam(name = ParameterName.KEYWORD)
                                                 @NotBlank(message = MessagePropertyKey.VALIDATION_GIFT_CERTIFICATE_KEYWORD_NOT_BLANK)
                                                         String keyword) {
        return service.findByKeyword(keyword);
    }

    /**
     * Delete gift certificate by it is ID.
     *
     * @param id Gift certificate ID.
     */
    @DeleteMapping(path = UrlMapping.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
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

        return EntityModel.of(gc,
                linkTo(GiftCertificateController.class).slash(gc.getId()).withSelfRel(),
                linkTo(methodOn(GiftCertificateController.class).findAll(new EsmPagination()))
                        .withRel("findAll").withType(HttpMethod.GET.name()),
                linkTo(methodOn(GiftCertificateController.class).findById(gc.getId()))
                        .withRel("findById").withType(HttpMethod.GET.name())
        );
    }
}
