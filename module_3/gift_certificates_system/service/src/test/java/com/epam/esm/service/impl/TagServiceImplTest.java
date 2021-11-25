package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.impl.TagDaoImpl;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.exception.EntityExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagServiceImplTest {
    @Mock
    private static TagDaoImpl tagDao;
    private TagServiceImpl tagService;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        tagService = new TagServiceImpl(tagDao);
    }

    @Test
    public void createPositive() {
        String tagName = "employee";

        Tag createdTag = new Tag();
        createdTag.setName(tagName);

        Tag expectedTag = new Tag();
        expectedTag.setId(4);
        expectedTag.setName(tagName);

        Mockito.when(tagDao.findByName(Mockito.eq(tagName))).thenReturn(Optional.empty());
        Mockito.when(tagDao.create(createdTag)).thenReturn(expectedTag);

        Tag actualTag = tagService.create(createdTag);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void createIfExistsPositive() {
        String existsTagName = "epam";

        Tag createdTag = new Tag();
        createdTag.setName(existsTagName);

        Tag expectedTag = new Tag();
        expectedTag.setId(1);
        expectedTag.setName(existsTagName);

        Optional<Tag> optionalTag = Optional.of(expectedTag);

        Mockito.when(tagDao.findByName(Mockito.eq(existsTagName))).thenReturn(optionalTag);

        Throwable throwable = assertThrows(EntityExistsException.class, () -> {
            tagService.create(createdTag);
        });

        String expectedMessage = "message.entity.exists.exception";
        String actualMessage = throwable.getMessage();

        assertTrue(expectedMessage.contentEquals(actualMessage));
    }

    @Test
    public void findAllPositive() {
        List<Tag> expectedTags = Arrays.asList(
                new Tag(1, "epam"),
                new Tag(2, "gift"),
                new Tag(3, "gym")
        );

        Mockito.when(tagDao.findAllBy()).thenReturn(expectedTags);

        List<Tag> actualTags = tagService.findAllBy();

        assertEquals(expectedTags, actualTags);
    }

    @Test
    public void findAllNegative() {
        List<Tag> expectedTags = Arrays.asList(
                new Tag(1, "gym"),
                new Tag(2, "gift"),
                new Tag(3, "epam")
        );

        Mockito.when(tagDao.findAllBy()).thenReturn(expectedTags);
        expectedTags = new ArrayList<>();
        List<Tag> actualTags = tagService.findAllBy();
        assertNotEquals(expectedTags, actualTags);
    }

    @Test
    public void findByIdPositiveOne() {
        int requiredId = 1;
        Tag expectedTag = new Tag(requiredId, "epam");
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagDao.findBy(requiredId)).thenReturn(optionalTag);
        Tag actualTag = tagService.findBy(requiredId);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void findByIdPositiveTwo() {
        int requiredId = 2;
        Tag expectedTag = new Tag(requiredId, "gift");
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagDao.findBy(requiredId)).thenReturn(optionalTag);
        Tag actualTag = tagService.findBy(requiredId);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void findByIdPositiveThree() {
        int requiredId = 3;

        Tag expectedTag = new Tag(requiredId, "gym");
        Optional<Tag> optionalTag = Optional.of(expectedTag);

        Mockito.when(tagDao.findBy(requiredId)).thenReturn(optionalTag);

        Tag actualTag = tagService.findBy(requiredId);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void findByIdExceptionPositive() {
        int nonExistingId = 9;

        Mockito.when(tagDao.findBy(nonExistingId)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(EntityNotFoundException.class, () -> tagService.findBy(nonExistingId));

        String expectedMessage = "message.entity.notFound.exception";
        String actualMessage = throwable.getMessage();

        assertTrue(expectedMessage.contentEquals(actualMessage));
    }

    @Test
    public void findByIdWithoutExceptionPositive() {
        int existsId = 3;
        Tag expectedTag = new Tag(3, "epam");

        Optional<Tag> optionalTag = Optional.of(expectedTag);

        Mockito.when(tagDao.findBy(existsId)).thenReturn(optionalTag);

        assertDoesNotThrow(() -> tagService.findBy(existsId));
    }

    @Test
    public void deletePositive() {
        int existsId = 2;

        Tag expectedTag = new Tag(2, "gift");
        Optional<Tag> optionalTag = Optional.of(expectedTag);

        Mockito.when(tagDao.findBy(existsId)).thenReturn(optionalTag);
        Mockito.when(tagDao.delete(existsId)).thenReturn(true);

        boolean actualResult = tagService.delete(existsId);
        assertTrue(actualResult);
    }

    @Test
    public void deleteNegative() {
        int existsId = 2;
        int nonExistingId = 9;

        Tag expectedTag = new Tag(2, "gift");
        Optional<Tag> optionalTag = Optional.of(expectedTag);

        Mockito.when(tagDao.findBy(existsId)).thenReturn(optionalTag);
        Mockito.when(tagDao.delete(nonExistingId)).thenReturn(false);

        boolean actualResult = tagService.delete(existsId);
        assertFalse(actualResult);
    }
}
