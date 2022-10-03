package com.epam.esm.service.impl;

<<<<<<< HEAD
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistsException;
import com.epam.esm.exception.EntityNotFoundException;
=======
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.util.EsmPagination;
import com.epam.esm.service.exception.EntityExistsException;
import com.epam.esm.service.exception.EntityNotFoundException;
>>>>>>> module_6
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> module_6
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
=======
import static org.junit.jupiter.api.Assertions.assertEquals;
>>>>>>> module_6
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagServiceImplTest {
    @Mock
<<<<<<< HEAD
    private static TagDaoImpl tagDao;
=======
    private TagRepository tagRepository;
>>>>>>> module_6
    private TagServiceImpl tagService;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
<<<<<<< HEAD
        tagService = new TagServiceImpl(tagDao);
=======
>>>>>>> module_6
    }

    @Test
    public void createPositive() {
        String tagName = "employee";

        Tag createdTag = new Tag();
        createdTag.setName(tagName);

        Tag expectedTag = new Tag();
<<<<<<< HEAD
        expectedTag.setId(4);
        expectedTag.setName(tagName);

        Mockito.when(tagDao.findByName(Mockito.eq(tagName))).thenReturn(Optional.empty());
        Mockito.when(tagDao.create(createdTag)).thenReturn(expectedTag);

        Tag actualTag = tagService.create(createdTag);
        assertEquals(expectedTag, actualTag);
=======
        expectedTag.setId(4L);
        expectedTag.setName(tagName);

        Mockito.when(tagRepository.findByName(Mockito.eq(tagName))).thenReturn(Optional.empty());
        Mockito.when(tagRepository.save(createdTag)).thenReturn(expectedTag);

        TagDto tagDto = new TagDto();
        tagDto.setName(tagName);
        assertEquals(tagService.create(tagDto).getId(), 4L);
>>>>>>> module_6
    }

    @Test
    public void createIfExistsPositive() {
        String existsTagName = "epam";

<<<<<<< HEAD
        Tag createdTag = new Tag();
        createdTag.setName(existsTagName);

        Tag expectedTag = new Tag();
        expectedTag.setId(1);
=======
        TagDto createdTag = new TagDto();
        createdTag.setName(existsTagName);

        Tag expectedTag = new Tag();
        expectedTag.setId(1L);
>>>>>>> module_6
        expectedTag.setName(existsTagName);

        Optional<Tag> optionalTag = Optional.of(expectedTag);

<<<<<<< HEAD
        Mockito.when(tagDao.findByName(Mockito.eq(existsTagName))).thenReturn(optionalTag);

        Throwable throwable = assertThrows(EntityExistsException.class, () -> {
            tagService.create(createdTag);
        });
=======
        Mockito.when(tagRepository.findByName(Mockito.eq(existsTagName))).thenReturn(optionalTag);

        Throwable throwable = assertThrows(EntityExistsException.class, () -> tagService.create(createdTag));
>>>>>>> module_6

        String expectedMessage = "message.entity.exists.exception";
        String actualMessage = throwable.getMessage();

        assertTrue(expectedMessage.contentEquals(actualMessage));
    }

    @Test
    public void findAllPositive() {
        List<Tag> expectedTags = Arrays.asList(
<<<<<<< HEAD
                new Tag(1, "epam"),
                new Tag(2, "gift"),
                new Tag(3, "gym")
        );

        Mockito.when(tagDao.findAll()).thenReturn(expectedTags);

        List<Tag> actualTags = tagService.findAll();

        assertEquals(expectedTags, actualTags);
=======
                new Tag(1L, "epam"),
                new Tag(2L, "gift"),
                new Tag(3L, "gym")
        );

        Mockito.when(tagRepository.findAll()).thenReturn(expectedTags);
        assertEquals(tagService.findAll(new EsmPagination()).getTotalElements(), 3L);
>>>>>>> module_6
    }

    @Test
    public void findAllNegative() {
        List<Tag> expectedTags = Arrays.asList(
<<<<<<< HEAD
                new Tag(1, "gym"),
                new Tag(2, "gift"),
                new Tag(3, "epam")
        );

        Mockito.when(tagDao.findAll()).thenReturn(expectedTags);
        expectedTags = new ArrayList<>();
        List<Tag> actualTags = tagService.findAll();
        assertNotEquals(expectedTags, actualTags);
    }

    @Test
    public void findByIdPositiveOne() {
        int requiredId = 1;
        Tag expectedTag = new Tag(requiredId, "epam");
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagDao.findById(requiredId)).thenReturn(optionalTag);
        Tag actualTag = tagService.findById(requiredId);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void findByIdPositiveTwo() {
        int requiredId = 2;
        Tag expectedTag = new Tag(requiredId, "gift");
        Optional<Tag> optionalTag = Optional.of(expectedTag);
        Mockito.when(tagDao.findById(requiredId)).thenReturn(optionalTag);
        Tag actualTag = tagService.findById(requiredId);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    public void findByIdPositiveThree() {
        int requiredId = 3;

        Tag expectedTag = new Tag(requiredId, "gym");
        Optional<Tag> optionalTag = Optional.of(expectedTag);

        Mockito.when(tagDao.findById(requiredId)).thenReturn(optionalTag);

        Tag actualTag = tagService.findById(requiredId);
        assertEquals(expectedTag, actualTag);
=======
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
>>>>>>> module_6
    }

    @Test
    public void findByIdExceptionPositive() {
<<<<<<< HEAD
        int nonExistingId = 9;

        Mockito.when(tagDao.findById(nonExistingId)).thenReturn(Optional.empty());
=======
        long nonExistingId = 9;

        Mockito.when(tagRepository.findById(nonExistingId)).thenReturn(Optional.empty());
>>>>>>> module_6

        Throwable throwable = assertThrows(EntityNotFoundException.class, () -> tagService.findById(nonExistingId));

        String expectedMessage = "message.entity.notFound.exception";
        String actualMessage = throwable.getMessage();

        assertTrue(expectedMessage.contentEquals(actualMessage));
    }
<<<<<<< HEAD

    @Test
    public void findByIdWithoutExceptionPositive() {
        int existsId = 3;
        Tag expectedTag = new Tag(3, "epam");

        Optional<Tag> optionalTag = Optional.of(expectedTag);

        Mockito.when(tagDao.findById(existsId)).thenReturn(optionalTag);

        assertDoesNotThrow(() -> tagService.findById(existsId));
    }

    @Test
    public void deletePositive() {
        int existsId = 2;

        Tag expectedTag = new Tag(2, "gift");
        Optional<Tag> optionalTag = Optional.of(expectedTag);

        Mockito.when(tagDao.findById(existsId)).thenReturn(optionalTag);
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

        Mockito.when(tagDao.findById(existsId)).thenReturn(optionalTag);
        Mockito.when(tagDao.delete(nonExistingId)).thenReturn(false);

        boolean actualResult = tagService.delete(existsId);
        assertFalse(actualResult);
    }
=======
>>>>>>> module_6
}
