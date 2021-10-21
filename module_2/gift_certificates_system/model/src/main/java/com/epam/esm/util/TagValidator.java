package com.epam.esm.util;

import com.epam.esm.entity.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class TagValidator {
    private static final String NAME_REGEX_VALID = "^\\p{L}{2,50}$";

    private TagValidator() {
    }

    public static boolean isValid(Set<Tag> tags) {
        return !ObjectUtils.isEmpty(tags) && tags.stream().allMatch(TagValidator::isValid);
    }

    public static boolean isValid(Tag entity) {
        String name = entity.getName();
        return isValidName(name);
    }

    private static boolean isValidName(String name) {
        boolean result = !ObjectUtils.isEmpty(name) && isFoundMatcher(name, NAME_REGEX_VALID);
        log.info("Tag name '{}' is valid: {}", name, result);
        return result;
    }

    private static boolean isFoundMatcher(String source, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }
}
