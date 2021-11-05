package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateUpdateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GitCertificateService;
import com.epam.esm.util.ParameterName;
import com.epam.esm.util.MessagePropertyKey;
import com.epam.esm.util.ResponseEntityWrapper;
import com.epam.esm.util.UrlMapping;
import com.epam.esm.util.pagination.EsmPagination;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GiftCertificateDto> create(@Valid @RequestBody GiftCertificateDto gcDto) {
        GiftCertificateDto gcCreatedDto = service.create(gcDto);
        return ResponseEntityWrapper.wrap(gcCreatedDto);
    }

    /**
     * Find list of gift certificates.
     *
     * @return - List of gift certificates.
     */
    @GetMapping
    public Set<GiftCertificateDto> findAll() {
        return service.findAll();
    }

    @GetMapping(params = ParameterName.SORT_BY)
    public Set<GiftCertificateDto> findAllSortByOrderBy(@RequestParam(value = ParameterName.SORT_BY) Set<String> sortBy,
                                                        @Valid EsmPagination esmPagination, BindingResult bindingResult) {
        return service.findAll(esmPagination, sortBy);
    }


    /**
     * Finding gift certificate by it is ID.
     *
     * @param id - Gift certificate ID.
     * @return - Entity of the gift certificate.
     */
    @GetMapping(path = UrlMapping.ID)
    public GiftCertificateDto findById(@Min(value = 1, message = MessagePropertyKey.VALIDATION_ID)
                                       @PathVariable long id) {
        return service.findById(id);
    }

    /**
     * Finding gift certificate by tag name.
     *
     * @param tagName - Tag name.
     * @return - List of gift certificates.
     */
    @GetMapping(params = ParameterName.TAG_NAME)
    public Set<GiftCertificateDto> findByTagName(@RequestParam(name = ParameterName.TAG_NAME)
                                                 @NotBlank(message = MessagePropertyKey.VALIDATION_TAG_NAME_NOT_EMPTY)
                                                         String tagName) {
        return service.findByTagName(tagName);
    }

    /**
     * Finding gift certificate by keyword.
     *
     * @param keyword - Keyword.
     * @return - List of gift certificates.
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
     * @param id - Gift certificate ID.
     */
    @DeleteMapping(path = UrlMapping.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    /**
     * Update gift certificate by it is ID.
     *
     * @param id                 - Gift certificate ID.
     * @param gcUpdateDto - Gift certificate DTO.
     */
    @PatchMapping(path = UrlMapping.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<GiftCertificateDto> update(@PathVariable long id,
                                                     @RequestBody GiftCertificateUpdateDto gcUpdateDto) {
        GiftCertificateDto updated = service.update(id, gcUpdateDto);
        return ResponseEntityWrapper.wrap(updated);
    }
}
