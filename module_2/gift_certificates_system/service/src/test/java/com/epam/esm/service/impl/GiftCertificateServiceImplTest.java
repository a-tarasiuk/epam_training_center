package com.epam.esm.service.impl;

import com.epam.esm.configuration.EsmConfigurationTest;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SortOperator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EsmConfigurationTest.class)
@Sql(scripts = "classpath:database/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GiftCertificateServiceImplTest {
    @Autowired
    private GiftCertificateServiceImpl giftCertificateService;
    private static GiftCertificateDaoImpl giftCertificateDao;
    private static TagDaoImpl tagDao;
    private static Tag tag;
    private static GiftCertificate existsGiftCertificateOne;
    private static GiftCertificate existsGiftCertificateTwo;
    private static GiftCertificate existsGiftCertificateThree;
    private static List<Tag> existsTagsOne;
    private static List<Tag> existsTagsTwo;
    private static List<Tag> existsTagsThree;


    @BeforeEach
    public void beforeAll() {
        giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
        tagDao = Mockito.mock(TagDaoImpl.class);
        tag = new Tag(4, "abstract tag");

        // Initialization gift certificates
        existsGiftCertificateOne = new GiftCertificate(1L, "certificate 1", "description for gift certificate 1", 1.10F, 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                null);
        existsGiftCertificateTwo = new GiftCertificate(2L, "certificate 2", "description for gift certificate 2", 2.20F, 2,
                LocalDateTime.of(2021, 10, 02, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 02, 17, 05, 53, 889000000),
                null);
        existsGiftCertificateThree = new GiftCertificate(3L, "certificate 3", "description for gift certificate 3", 3.30F, 3,
                LocalDateTime.of(2021, 10, 03, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 03, 17, 05, 53, 889000000),
                null);

        // Initialization tags
        existsTagsOne = Arrays.asList(
                new Tag(1, "epam"),
                new Tag(2, "gift"),
                new Tag(3, "gym")
        );
        existsTagsTwo = Collections.singletonList(new Tag(2, "gift"));
        existsTagsThree = Collections.singletonList(new Tag(3, "gym"));
    }

    @Test
    public void createPositive() {
        GiftCertificate giftCertificate = new GiftCertificate(4L, "certificate 4", "description for gift certificate 4", 1.10F, 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                Collections.singletonList(tag));

        Mockito.when(giftCertificateDao.findByName("certificate 4")).thenReturn(Optional.empty());
        Mockito.when(giftCertificateDao.create(giftCertificate)).thenReturn(giftCertificate);
        assertNotNull(giftCertificateService.create(giftCertificate));
    }

    @Test
    public void createException() {
        String tagName = "certificate 1";

        GiftCertificate giftCertificate = new GiftCertificate(4L, tagName, "description for gift certificate 4", 1.10F, 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                Collections.singletonList(tag));

        Mockito.when(giftCertificateDao.findByName(tagName)).thenThrow(EntityExistsException.class);

        Throwable throwable = assertThrows(EntityExistsException.class, () -> giftCertificateService.create(giftCertificate));

        String expectedMessage = "Gift certificate with name '" + tagName + "' already exists in the database.";
        String actualMessage = throwable.getMessage();

        assertTrue(expectedMessage.contentEquals(actualMessage));
    }

    @Test
    public void findAllPositive() {
        List<GiftCertificate> giftCertificates = Arrays.asList(
                existsGiftCertificateOne,
                existsGiftCertificateTwo,
                existsGiftCertificateThree
        );

        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);

        existsGiftCertificateOne.setTags(existsTagsOne);
        existsGiftCertificateTwo.setTags(existsTagsTwo);
        existsGiftCertificateThree.setTags(existsTagsThree);

        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(
                existsGiftCertificateOne,
                existsGiftCertificateTwo,
                existsGiftCertificateThree);

        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAll();

        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    public void findAllAndSortNameAndDescPositive() {
        // Step 1
        List<GiftCertificate> giftCertificates = Arrays.asList(
                existsGiftCertificateOne,
                existsGiftCertificateTwo,
                existsGiftCertificateThree
        );

        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);

        // Step 2
        existsGiftCertificateOne.setTags(existsTagsOne);
        existsGiftCertificateTwo.setTags(existsTagsTwo);
        existsGiftCertificateThree.setTags(existsTagsThree);

        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(
                existsGiftCertificateThree,
                existsGiftCertificateTwo,
                existsGiftCertificateOne
                );

        // Step 3
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllAndSort(ColumnName.NAME, SortOperator.DESC);

        // Result
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    public void findAllAndSortCreateDateAndDescPositive() {
        // Step 1
        List<GiftCertificate> giftCertificates = Arrays.asList(
                existsGiftCertificateOne,
                existsGiftCertificateTwo,
                existsGiftCertificateThree
        );

        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);

        // Step 2
        existsGiftCertificateOne.setTags(existsTagsOne);
        existsGiftCertificateTwo.setTags(existsTagsTwo);
        existsGiftCertificateThree.setTags(existsTagsThree);

        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(
                existsGiftCertificateThree,
                existsGiftCertificateTwo,
                existsGiftCertificateOne
        );

        // Step 3
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllAndSort(ColumnName.CREATE_DATE, SortOperator.DESC);

        // Result
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    public void findAllAndSortLastUpdateDateAndDescPositive() {
        // Step 1
        List<GiftCertificate> giftCertificates = Arrays.asList(
                existsGiftCertificateOne,
                existsGiftCertificateTwo,
                existsGiftCertificateThree
        );

        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);

        // Step 2
        existsGiftCertificateOne.setTags(existsTagsOne);
        existsGiftCertificateTwo.setTags(existsTagsTwo);
        existsGiftCertificateThree.setTags(existsTagsThree);

        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(
                existsGiftCertificateThree,
                existsGiftCertificateTwo,
                existsGiftCertificateOne
        );

        // Step 3
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllAndSort(ColumnName.LAST_UPDATE_DATE, SortOperator.DESC);

        // Result
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    public void findAllAndSortWithoutSortingAndDescPositive() {
        // Step 1
        List<GiftCertificate> giftCertificates = Arrays.asList(
                existsGiftCertificateOne,
                existsGiftCertificateTwo,
                existsGiftCertificateThree
        );

        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);

        // Step 2
        existsGiftCertificateOne.setTags(existsTagsOne);
        existsGiftCertificateTwo.setTags(existsTagsTwo);
        existsGiftCertificateThree.setTags(existsTagsThree);

        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(
                existsGiftCertificateThree,
                existsGiftCertificateTwo,
                existsGiftCertificateOne
        );

        // Step 3
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllAndSort(ColumnName.WITHOUT_SORTING, SortOperator.DESC);

        // Result
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    public void findByIdPositive() {
        long certificateId = 1;
        Mockito.when(giftCertificateDao.findById(certificateId)).thenReturn(Optional.of(existsGiftCertificateOne));
        Mockito.when(tagDao.findByGiftCertificateId(certificateId)).thenReturn(existsTagsOne);
        existsGiftCertificateOne.setTags(existsTagsOne);
        GiftCertificate actualGiftCertificate = giftCertificateService.findById(certificateId);
        assertEquals(existsGiftCertificateOne, actualGiftCertificate);
    }

    @Test
    public void findByIdExceptionPositive() {
        long nonExistsCertificateId = 100;
        Mockito.when(giftCertificateDao.findById(nonExistsCertificateId)).thenThrow(EntityNotFoundException.class);
        Throwable throwable = assertThrows(EntityNotFoundException.class, () -> giftCertificateService.findById(nonExistsCertificateId));
        assertNotNull(throwable);
    }

    @Test
    public void findByTagNamePositive() {
        long tagId = 1;
        String existingTagName = "epam";
        Tag epamTag = new Tag(tagId, existingTagName);
        Optional<Tag> optionalTag = Optional.of(epamTag);

        Mockito.when(tagDao.findByName(existingTagName)).thenReturn(optionalTag);

        List<GiftCertificate> giftCertificates = Collections.singletonList(existsGiftCertificateOne);

        Mockito.when(giftCertificateDao.findByTagId(tagId)).thenReturn(giftCertificates);
        Mockito.when(tagDao.findByGiftCertificateId(1)).thenReturn(existsTagsOne);

        existsGiftCertificateOne.setTags(existsTagsOne);
        List<GiftCertificate> expectedGiftCertificates = Collections.singletonList(existsGiftCertificateOne);
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findByTagName(existingTagName);

        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    public void findByTagNameException() {
        String nonExistingTagName = "xxxxxxxxx";
        Mockito.when(tagDao.findByName(nonExistingTagName)).thenThrow(EntityNotFoundException.class);
        Throwable throwable = assertThrows(EntityNotFoundException.class, () -> giftCertificateService.findByTagName(nonExistingTagName));
        assertNotNull(throwable);
    }
}
