package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.ParameterName;
import com.epam.esm.util.ResponseEntityWrapper;
import com.epam.esm.util.SqlSortOperator;
import com.epam.esm.util.UrlMapping;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import java.util.List;
import java.util.Set;

/**
 * Gift certificate controller.
 * Working with the gift certificate SERVICE layer.
 *
 * @see com.epam.esm.service.impl.GiftCertificateServiceImpl
 */
@Log4j2
@RestController
@RequestMapping(value = UrlMapping.GIFT_CERTIFICATES, produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {
    private final GiftCertificateServiceImpl service;

    /**
     * Initializing of the gift certificate implementation.
     *
     * @param service - Service layer of the gift certificate.
     */
    @Autowired
    public GiftCertificateController(GiftCertificateServiceImpl service) {
        this.service = service;
    }


    /**
     * Create gift certificate entity.
     *
     * @param giftCertificate - Gift certificate.
     * @return - Extension of HttpEntity that adds an HttpStatus status code.
     * Used in RestTemplate as well as in @Controller methods.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificate> create(@RequestBody GiftCertificate giftCertificate) {
        GiftCertificate createdGiftCertificate = service.create(giftCertificate);
        return ResponseEntityWrapper.wrap(createdGiftCertificate);
    }

    /**
     * Find list of gift certificates.
     *
     * @return - List of gift certificates.
     */
    @GetMapping
    public List<GiftCertificate> findAll() {
        return service.findAll();
    }

    /**
     * Find list of gift certificates and sort them by column name and ASC\DESC.
     *
     * @return - List of sorted gift certificates.
     * @see com.epam.esm.util.ParameterName
     * @see com.epam.esm.util.ColumnName
     * @see com.epam.esm.util.SqlSortOperator
     */
    @GetMapping(params = {ParameterName.SORT_BY})
    public List<GiftCertificate> findAllSorted(@RequestParam(value = ParameterName.SORT_BY) Set<ColumnName> columnNames) {
        return service.findAllSorted(columnNames);
    }

    /**
     * Find list of gift certificates and sort them by column name and ASC\DESC.
     *
     * @param sortBy - Column name.
     * @return - List of sorted gift certificates.
     * @see com.epam.esm.util.ParameterName
     * @see com.epam.esm.util.ColumnName
     * @see com.epam.esm.util.SqlSortOperator
     */
    @GetMapping(params = {ParameterName.SORT_BY, ParameterName.SORT_TYPE})
    public List<GiftCertificate> findAllSortedWithSortType(@RequestParam(value = ParameterName.SORT_BY) Set<ColumnName> sortBy,
                                                           @RequestParam(value = ParameterName.SORT_TYPE) SqlSortOperator sqlSortOperator) {
        return service.findAllSortedWithType(sortBy, sqlSortOperator);
    }

    /**
     * Finding gift certificate by it is ID.
     *
     * @param id - Gift certificate ID.
     * @return - Entity of the gift certificate.
     */
    @GetMapping(path = UrlMapping.ID)
    public GiftCertificate findById(@PathVariable(ParameterName.ID) long id) {
        return service.findById(id);
    }

    /**
     * Finding gift certificate by tag name.
     *
     * @param tagName - Tag name.
     * @return - List of gift certificates.
     */
    @GetMapping(params = ParameterName.TAG_NAME)
    public List<GiftCertificate> findByTagName(@RequestParam(value = ParameterName.TAG_NAME) String tagName) {
        return service.findByTagName(tagName);
    }

    /**
     * Finding gift certificate by part of gift certificate name.
     *
     * @param partOfName - Part of name.
     * @return - List of gift certificates.
     */
    @GetMapping(params = ParameterName.PART_OF_NAME)
    public List<GiftCertificate> findByPartOfName(@RequestParam(value = ParameterName.PART_OF_NAME) String partOfName) {
        return service.findByPartOfName(partOfName);
    }

    /**
     * Finding gift certificate by part of gift certificate description.
     *
     * @param partOfDescription - Part of description.
     * @return - List of gift certificates.
     */
    @GetMapping(params = ParameterName.PART_OF_DESCRIPTION)
    public List<GiftCertificate> findByPartOfDescription(@RequestParam(value = ParameterName.PART_OF_DESCRIPTION) String partOfDescription) {
        return service.findByPartOfDescription(partOfDescription);
    }

    /**
     * Finding gift certificate by keyword.
     *
     * @param keyword - Keyword.
     * @return - List of gift certificates.
     */
    @GetMapping(params = ParameterName.KEYWORD)
    public List<GiftCertificate> findByKeyword(@RequestParam String keyword) {
        return service.findByKeyword(keyword);
    }

    /**
     * Delete gift certificate by it is ID.
     *
     * @param id - Gift certificate ID.
     * @return - Operation result (true - if deleted, false - if not deleted)
     */
    @DeleteMapping(path = UrlMapping.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable long id) {
        return service.delete(id);
    }

    /**
     * Update gift certificate by it is ID.
     *
     * @param id              - Gift certificate ID.
     * @param giftCertificate - Entity of the gift certificate.
     */
    @PatchMapping(path = UrlMapping.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id, @RequestBody GiftCertificate giftCertificate) {
        service.update(id, giftCertificate);
    }
}
