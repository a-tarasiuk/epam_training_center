package com.epam.esm.dao.impl;

import com.epam.esm.configuration.EsmConfigurationTest;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EsmConfigurationTest.class)
@Sql(scripts = "classpath:database/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TagDaoImplTest {
    private final static int EXPECTED_COUNT_TAGS_POSITIVE = 3;
    private final static int EXPECTED_COUNT_TAGS_NEGATIVE = 5;
    private final static long EXPECTED_TAG_ID_POSITIVE = 1;
    private final static long EXPECTED_TAG_ID_NEGATIVE = 8;
    private final static String EPAM_TAG_NAME = "epam";
    private final static String GIFT_TAG_NAME = "gift";
    private final static String GYM_TAG_NAME = "gym";
    private final static String EXPECTED_TAG_NAME_POSITIVE = "notebook";
    private final static String EXPECTED_TAG_NAME_NEGATIVE = "negative";
    private static Tag EXPECTED_TAG_POSITIVE;
    private static Tag EXPECTED_TAG_NEGATIVE;
    private static List<Tag> EXPECTED_POSITIVE_TAGS;
    private static List<Tag> EXPECTED_NEGATIVE_TAGS;
    private final TagDaoImpl tagDao;

    @Autowired
    public TagDaoImplTest(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }

    @BeforeEach
    public void init() {
        // initialize expected positive Tag
        EXPECTED_TAG_POSITIVE = new Tag();
        EXPECTED_TAG_POSITIVE.setId(EXPECTED_TAG_ID_POSITIVE);
        EXPECTED_TAG_POSITIVE.setName(EXPECTED_TAG_NAME_POSITIVE);

        // initialize expected negative Tag
        EXPECTED_TAG_NEGATIVE = new Tag();
        EXPECTED_TAG_NEGATIVE.setId(EXPECTED_TAG_ID_NEGATIVE);
        EXPECTED_TAG_POSITIVE.setName(EXPECTED_TAG_NAME_POSITIVE);

        // initialize expected positive Tag list
        EXPECTED_POSITIVE_TAGS = Arrays.asList(new Tag(1, EPAM_TAG_NAME),
                new Tag(2, GIFT_TAG_NAME),
                new Tag(3, GYM_TAG_NAME));

        // initialize expected negative Tag list
        EXPECTED_NEGATIVE_TAGS = Arrays.asList(new Tag(9, EPAM_TAG_NAME),
                new Tag(9, GIFT_TAG_NAME),
                new Tag(9, GYM_TAG_NAME));
    }

    @Test
    public void createForNamePositive() {
        Tag actualTag = tagDao.create(EXPECTED_TAG_POSITIVE);
        String actualTagName = actualTag.getName();
        assertEquals(EXPECTED_TAG_NAME_POSITIVE, actualTagName);
    }

    @Test
    public void createForNameNegative() {
        Tag actualTag = tagDao.create(EXPECTED_TAG_POSITIVE);
        String actualTagName = actualTag.getName();
        assertNotEquals(EXPECTED_TAG_NAME_NEGATIVE, actualTagName);
    }

    @Test
    public void createForEqualsPositive() {
        Tag actualTag = tagDao.create(EXPECTED_TAG_POSITIVE);
        assertEquals(EXPECTED_TAG_POSITIVE, actualTag);
    }

    @Test
    public void createForEqualsNegative() {
        Tag actualTag = tagDao.create(EXPECTED_TAG_POSITIVE);
        assertNotEquals(EXPECTED_TAG_NEGATIVE, actualTag);
    }

    @Test
    public void findAllForEqualsPositive() {
        List<Tag> actualTags = tagDao.findAll();
        assertEquals(EXPECTED_POSITIVE_TAGS, actualTags);
    }

    @Test
    public void findAllForEqualsNegative() {
        List<Tag> actualTags = tagDao.findAll();
        assertNotEquals(EXPECTED_NEGATIVE_TAGS, actualTags);
    }

    @Test
    public void findAllForSizePositive() {
        List<Tag> actualTags = tagDao.findAll();
        int actualCountTags = actualTags.size();
        assertEquals(EXPECTED_COUNT_TAGS_POSITIVE, actualCountTags);
    }

    @Test
    public void findAllForSizeNegative() {
        List<Tag> actualTags = tagDao.findAll();
        int actualCountTags = actualTags.size();
        assertNotEquals(EXPECTED_COUNT_TAGS_NEGATIVE, actualCountTags);
    }
}
