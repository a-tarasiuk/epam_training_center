package com.epam.esm.controller;

import com.epam.esm.util.ColumnName;
import com.epam.esm.util.ResponseEntityWrapper;
import com.epam.esm.util.SortOperator;
import com.epam.esm.util.StringToColumnNameEnumConverter;
import com.epam.esm.util.StringToSortOperatorEnumConverter;
import com.epam.esm.util.UrlMapping;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import javax.validation.Valid;
import com.epam.esm.util.ParameterName;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
    private final GiftCertificateServiceImpl giftCertificateService;

    /**
     * Initializing of the gift certificate implementation.
     *
     * @param giftCertificateService - Service layer of the gift certificate.
     */
    @Autowired
    public GiftCertificateController(GiftCertificateServiceImpl giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }


    /**
     * Create gift certificate entity.
     *
     * @param giftCertificate - Gift certificate.
     * @param bindingResult   - General interface that represents binding results.
     *                        Extends the interface for error registration capabilities,
     *                        allowing for a Validator to be applied, and adds binding-specific analysis and model building.
     * @return - Extension of HttpEntity that adds an HttpStatus status code.
     * Used in RestTemplate as well as in @Controller methods.
     */
    @PostMapping(value = UrlMapping.CREATE_ENTITY, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificate> create(@RequestBody @Valid GiftCertificate giftCertificate, BindingResult bindingResult) {
        log.debug("Create '{}'.", giftCertificate);
        ResponseEntity<GiftCertificate> responseEntity;

        if (bindingResult.hasErrors()) {
            log.debug(giftCertificate + " has invalid data.");
            responseEntity = ResponseEntity.badRequest().build();
        } else {
            log.debug(giftCertificate + " is valid.");
            GiftCertificate createdGiftCertificate = giftCertificateService.create(giftCertificate);
            responseEntity = ResponseEntityWrapper.wrap(createdGiftCertificate);
        }

        return responseEntity;
    }

    /**
     * Find list of gift certificates.
     *
     * @return - List of gift certificates.
     */
    @GetMapping
    public List<GiftCertificate> findAll() {
        return giftCertificateService.findAll();
    }

    /**
     * Find list of gift certificates and sort them by column name and ASC\DESC.
     *
     * @param sortBy   - Column name.
     * @param sortType - ASC\DESC.
     * @return - List of sorted gift certificates.
     * @see com.epam.esm.util.ParameterName
     * @see com.epam.esm.util.ColumnName
     * @see com.epam.esm.util.SortOperator
     */
    @GetMapping(params = {ParameterName.SORT_BY, ParameterName.SORT_TYPE})
    public List<GiftCertificate> findAllAndSort(@RequestParam(value = ParameterName.SORT_BY) String sortBy,
                                                @RequestParam(value = ParameterName.SORT_TYPE, required = false) String sortType) {
        StringToColumnNameEnumConverter columnNameEnumConverter = new StringToColumnNameEnumConverter();
        ColumnName columnName = columnNameEnumConverter.convert(sortBy);

        StringToSortOperatorEnumConverter sortOperatorEnumConverter = new StringToSortOperatorEnumConverter();
        SortOperator sortOperator = sortOperatorEnumConverter.convert(sortType);

        return giftCertificateService.findAllAndSort(columnName, sortOperator);
    }

    /**
     * Finding gift certificate by it is ID.
     *
     * @param id - Gift certificate ID.
     * @return - Entity of the gift certificate.
     */
    @GetMapping(path = UrlMapping.ID)
    public GiftCertificate findById(@PathVariable(ParameterName.ID) long id) {
        log.debug("Find gift certificate by id '{}'.", id);
        return giftCertificateService.findById(id);
    }

    /**
     * Finding gift certificate by tag name.
     *
     * @param tagName - Tag name.
     * @return - List of gift certificates.
     */
    @GetMapping(params = ParameterName.TAG_NAME)
    public List<GiftCertificate> findByTagName(@RequestParam(value = ParameterName.TAG_NAME) String tagName) {
        log.debug("Find gift certificates by tag name '{}'.", tagName);
        return giftCertificateService.findByTagName(tagName);
    }

    /**
     * Finding gift certificate by part of gift certificate name.
     *
     * @param partOfName - Part of name.
     * @return - List of gift certificates.
     */
    @GetMapping(params = ParameterName.PART_OF_NAME)
    public List<GiftCertificate> findByPartOfName(@RequestParam(value = ParameterName.PART_OF_NAME) String partOfName) {
        log.debug("Find gift certificates by part of name '{}'.", partOfName);
        return giftCertificateService.findByPartOfName(partOfName);
    }

    /**
     * Finding gift certificate by part of gift certificate description.
     *
     * @param partOfDescription - Part of description.
     * @return - List of gift certificates.
     */
    @GetMapping(params = ParameterName.PART_OF_DESCRIPTION)
    public List<GiftCertificate> findByPartOfDescription(@RequestParam(value = ParameterName.PART_OF_DESCRIPTION) String partOfDescription) {
        log.debug("Find gift certificates by part of description '{}'.", partOfDescription);
        return giftCertificateService.findByPartOfDescription(partOfDescription);
    }

    /**
     * Finding gift certificate by keyword.
     *
     * @param keyword - Keyword.
     * @return - List of gift certificates.
     */
    @GetMapping(params = ParameterName.KEYWORD)
    public List<GiftCertificate> findByKeyword(@RequestParam String keyword) {
        log.debug("Find gift certificates by keyword '{}'.", keyword);
        return giftCertificateService.findByKeyword(keyword);
    }

    /**
     * Delete gift certificate by it is ID.
     *
     * @param id - Gift certificate ID.
     * @return - Operation result (true - if deleted, false - if not deleted)
     */
    @DeleteMapping(path = UrlMapping.ID)
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable long id) {
        log.debug("Delete gift certificate by id '{}'.", id);
        return giftCertificateService.delete(id);
    }

    /**
     * Update gift certificate by it is ID.
     *
     * @param id              - Gift certificate ID.
     * @param giftCertificate - Entity of the gift certificate.
     */
    @PutMapping(path = UrlMapping.ID)
    public void update(@PathVariable long id, @RequestBody GiftCertificate giftCertificate) {
        log.debug("Update gift certificate by id '{}'.", id);
        giftCertificateService.update(id, giftCertificate);
    }
}
