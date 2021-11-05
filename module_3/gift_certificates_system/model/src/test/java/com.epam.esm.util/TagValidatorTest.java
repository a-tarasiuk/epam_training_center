package com.epam.esm.util;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagValidatorTest {
    Tag tag;
    Set<Tag> tags;

    @BeforeEach
    public void beforeEach() {
        tag = new Tag();
        tag.setName("employee");
    }

    @Test
    public void isValidPositive() {
        boolean result = TagValidator.isValid(tag);
        assertTrue(result);
    }

    @Test
    public void isValidNegative1() {
        tag.setName("2");
        boolean result = TagValidator.isValid(tag);
        assertFalse(result);
    }

    @Test
    public void isValidNegative2() {
        tag.setName("a");
        boolean result = TagValidator.isValid(tag);
        assertFalse(result);
    }

    @Test
    public void isValidNegative3() {
        tag.setName("A");
        boolean result = TagValidator.isValid(tag);
        assertFalse(result);
    }

    @Test
    public void isValidListPositive() {
        tags = new HashSet<>(Arrays.asList(tag, tag, tag));

        boolean result = TagValidator.isValid(tags);
        assertTrue(result);
    }

    @Test
    public void isValidListNegative1() {
        Tag invalid = new Tag(1L, "a");
        tags = new HashSet<>(Arrays.asList(tag, invalid));

        boolean result = TagValidator.isValid(tags);
        assertFalse(result);
    }

    @Test
    public void isValidListNegative2() {
        Tag invalid = new Tag(1L, "A");
        tags = new HashSet<>(Arrays.asList(tag, invalid));

        boolean result = TagValidator.isValid(tags);
        assertFalse(result);
    }

    @Test
    public void isValidListNegative3() {
        Tag invalid = new Tag(1L, "Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        tags = new HashSet<>(Arrays.asList(tag, invalid));

        boolean result = TagValidator.isValid(tags);
        assertFalse(result);
    }

    @Test
    public void isValidListNegative4() {
        Tag invalid = new Tag(1L, "                         ");
        tags = new HashSet<>(Arrays.asList(tag, invalid));

        boolean result = TagValidator.isValid(tags);
        assertFalse(result);
    }
}
