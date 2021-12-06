package com.epam.esm.service.impl;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.service.exception.EntityExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;
    private TagServiceImpl tagService;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPositive() {
        String tagName = "employee";

        Tag createdTag = new Tag();
        createdTag.setName(tagName);

        Tag expectedTag = new Tag();
        expectedTag.setId(4L);
        expectedTag.setName(tagName);

        Mockito.when(tagRepository.findByName(Mockito.eq(tagName))).thenReturn(Optional.empty());
        Mockito.when(tagRepository.save(createdTag)).thenReturn(expectedTag);

        TagDto tagDto = new TagDto();
        tagDto.setName(tagName);
        assertEquals(tagService.create(tagDto).getId(), 4L);
    }

    @Test
    public void createIfExistsPositive() {
        String existsTagName = "epam";

        TagDto createdTag = new TagDto();
        createdTag.setName(existsTagName);

        Tag expectedTag = new Tag();
        expectedTag.setId(1L);
        expectedTag.setName(existsTagName);

        Optional<Tag> optionalTag = Optional.of(expectedTag);

        Mockito.when(tagRepository.findByName(Mockito.eq(existsTagName))).thenReturn(optionalTag);

        Throwable throwable = assertThrows(EntityExistsException.class, () -> tagService.create(createdTag));

        String expectedMessage = "message.entity.exists.exception";
        String actualMessage = throwable.getMessage();

        assertTrue(expectedMessage.contentEquals(actualMessage));
    }

    @Test
    public void findAllPositive() {
        List<Tag> expectedTags = Arrays.asList(
                new Tag(1L, "epam"),
                new Tag(2L, "gift"),
                new Tag(3L, "gym")
        );

        Mockito.when(tagRepository.findAll()).thenReturn(expectedTags);
        assertEquals(tagService.findAll(new EsmPagination()).getTotalElements(), 3L);
    }

    @Test
    public void findAllNegative() {
        List<Tag> expectedTags = Arrays.asList(
                new Tag(1L, "gym"),
                new Tag(2L, "gift"),
                new Tag(3L, "epam")
        );

        Mockito.when(tagRepository.findAll()).thenReturn(expectedTags);
        assertEquals(tagService.findAll(new EsmPagination()).getTotalElements(), 999L);
    }

    @Test
    public void findByIdPositive() {
        TagDto expectedTag = new TagDto();
        expectedTag.setId(1L);
        expectedTag.setName("epam");

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("epam");

        Mockito.when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        assertEquals(tagService.findById(1L), expectedTag);
    }

    @Test
    public void findByIdExceptionPositive() {
        long nonExistingId = 9;

        Mockito.when(tagRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(EntityNotFoundException.class, () -> tagService.findById(nonExistingId));

        String expectedMessage = "message.entity.notFound.exception";
        String actualMessage = throwable.getMessage();

        assertTrue(expectedMessage.contentEquals(actualMessage));
    }
}
