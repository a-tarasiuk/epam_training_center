package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class GiftCertificateValidator {
    private static final String NAME_REGEX_VALID = "^\\p{L}{2,50}$";
    private static final String DESCRIPTION_REGEX_VALID = "^\\p{L}[\\p{L}\\p{Zs}]{2,200}[^\\p{Z}]$";
    private static final String PRICE_REGEX_VALID = "^\\p{Nd}{1,10}(?:[.,]\\p{Nd}{0,2})?$";
    private static final int COUNT_DURATION_MAX = 365;
    private static final int COUNT_DURATION_MIN = 0;

    private GiftCertificateValidator() {
    }

    public static boolean isValid(GiftCertificate entity) {
        String name = entity.getName();
        String description = entity.getDescription();
        Float price = entity.getPrice();
        Integer duration = entity.getDuration();

        return isValidName(name) && isValidDescription(description) && isValidPrice(price) && isValidDuration(duration);
    }

    private static boolean isValidName(String name) {
        boolean result = !ObjectUtils.isEmpty(name) && isFoundMatcher(name, NAME_REGEX_VALID);
        log.info("Name '{}' is valid: {}", name, result);
        return result;
    }

    private static boolean isValidDescription(String description) {
        boolean result = !ObjectUtils.isEmpty(description) && isFoundMatcher(description, DESCRIPTION_REGEX_VALID);
        log.info("Description '{}' is valid: {}", description, result);
        return result;
    }

    private static boolean isValidPrice(Float price) {
        boolean result = !ObjectUtils.isEmpty(price) && isFoundMatcher(Float.toString(price), PRICE_REGEX_VALID);
        log.info("Price '{}' is valid: {}", price, result);
        return result;
    }

    private static boolean isValidDuration(Integer duration) {
        boolean result = !ObjectUtils.isEmpty(duration) && duration > COUNT_DURATION_MIN && duration < COUNT_DURATION_MAX;
        log.info("Duration '{}' is valid: {}", duration, result);
        return result;
    }

    private static boolean isValidTags(List<Tag> tags) {
        boolean result = !ObjectUtils.isEmpty(tags);
        log.info("Tags '{}' is valid: {}", tags, result);
        return result;
    }

    private static boolean isFoundMatcher(String source, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }
}
