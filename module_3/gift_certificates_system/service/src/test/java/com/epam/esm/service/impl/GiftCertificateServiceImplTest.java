//package com.epam.esm.service.impl;
//
//import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
//import com.epam.esm.dao.impl.GiftCertificateToTagRelationDaoImpl;
//import com.epam.esm.dao.impl.TagDaoImpl;
//import com.epam.esm.entity.GiftCertificate;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exception.EntityNotFoundException;
//import com.epam.esm.util.ColumnName;
//import com.epam.esm.util.SqlSortOperator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//public class GiftCertificateServiceImplTest {
//    @Mock
//    private static GiftCertificateDaoImpl giftCertificateDao;
//    @Mock
//    private static TagDaoImpl tagDao;
//    @Mock
//    private static GiftCertificateToTagRelationDaoImpl relationDao;
//
//    private static Tag tag;
//    private static GiftCertificate existsGiftCertificateOne;
//    private static GiftCertificate existsGiftCertificateTwo;
//    private static GiftCertificate existsGiftCertificateThree;
//    private static Set<Tag> existsTagsOne;
//    private static Set<Tag> existsTagsTwo;
//    private static Set<Tag> existsTagsThree;
//    private GiftCertificateServiceImpl giftCertificateService;
//
//    @BeforeEach
//    public void beforeAll() {
//        MockitoAnnotations.openMocks(this);
//        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, tagDao, relationDao);
//        tag = new Tag(4, "abstract tag");
//
//        // Initialization gift certificates
//        existsGiftCertificateOne = new GiftCertificate(1L, "certificate 1", "description for gift certificate 1", BigDecimal.valueOf(1.10F), 1,
//                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
//                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
//                null);
//        existsGiftCertificateTwo = new GiftCertificate(2L, "certificate 2", "description for gift certificate 2", BigDecimal.valueOf(2.20F), 2,
//                LocalDateTime.of(2021, 10, 02, 17, 05, 53, 889000000),
//                LocalDateTime.of(2021, 10, 02, 17, 05, 53, 889000000),
//                null);
//        existsGiftCertificateThree = new GiftCertificate(3L, "certificate 3", "description for gift certificate 3", BigDecimal.valueOf(3.30F), 3,
//                LocalDateTime.of(2021, 10, 03, 17, 05, 53, 889000000),
//                LocalDateTime.of(2021, 10, 03, 17, 05, 53, 889000000),
//                null);
//
//        // Initialization tags
//        existsTagsOne = new HashSet<Tag>(Arrays.asList(
//                new Tag(1, "epam"),
//                new Tag(2, "gift"),
//                new Tag(3, "gym")
//        ));
//        existsTagsTwo = Collections.singleton(new Tag(2, "gift"));
//        existsTagsThree = Collections.singleton(new Tag(3, "gym"));
//    }
//
//    @Test
//    public void createPositive() {
//        GiftCertificate giftCertificate = new GiftCertificate(4L, "certificate", "description for gift certificate 4", BigDecimal.valueOf(1.10F), 1,
//                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
//                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
//                Collections.singleton(tag));
//
//        Mockito.when(giftCertificateDao.findByName("certificate")).thenReturn(Optional.empty());
//        Mockito.when(giftCertificateDao.create(giftCertificate)).thenReturn(giftCertificate);
//        Mockito.when(tagDao.findByName("abstract tag")).thenReturn(Optional.of(tag));
//        Mockito.when(relationDao.find(1, 1)).thenReturn(Optional.empty());
//        Mockito.when(relationDao.create(1, 1)).thenReturn(true);
//        assertNotNull(giftCertificateService.create(giftCertificate));
//    }
//
//    @Test
//    public void findAllPositive() {
//        List<GiftCertificate> giftCertificates = Arrays.asList(
//                existsGiftCertificateOne,
//                existsGiftCertificateTwo,
//                existsGiftCertificateThree
//        );
//
//        Mockito.when(giftCertificateDao.findAllBy()).thenReturn(giftCertificates);
//
//        existsGiftCertificateOne.setTags(existsTagsOne);
//        existsGiftCertificateTwo.setTags(existsTagsTwo);
//        existsGiftCertificateThree.setTags(existsTagsThree);
//
//        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(
//                existsGiftCertificateOne,
//                existsGiftCertificateTwo,
//                existsGiftCertificateThree);
//
//        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllBy();
//
//        assertEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
//
//    @Test
//    public void findAllAndSortNameAndDescPositive() {
//        // Step 1
//        List<GiftCertificate> giftCertificates = Arrays.asList(
//                existsGiftCertificateOne,
//                existsGiftCertificateTwo,
//                existsGiftCertificateThree
//        );
//
//        Mockito.when(giftCertificateDao.findAllBy()).thenReturn(giftCertificates);
//
//        // Step 2
//        existsGiftCertificateOne.setTags(existsTagsOne);
//        existsGiftCertificateTwo.setTags(existsTagsTwo);
//        existsGiftCertificateThree.setTags(existsTagsThree);
//
//        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(
//                existsGiftCertificateThree,
//                existsGiftCertificateTwo,
//                existsGiftCertificateOne
//        );
//
//        Set<ColumnName> set = Collections.singleton(ColumnName.NAME);
//
//        // Step 3
//        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllSortedWithType(set, SqlSortOperator.DESC);
//
//        // Result
//        assertEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
//
//    @Test
//    public void findAllAndSortCreateDateAndDescPositive() {
//        // Step 1
//        List<GiftCertificate> giftCertificates = Arrays.asList(
//                existsGiftCertificateOne,
//                existsGiftCertificateTwo,
//                existsGiftCertificateThree
//        );
//
//        Mockito.when(giftCertificateDao.findAllBy()).thenReturn(giftCertificates);
//
//        // Step 2
//        existsGiftCertificateOne.setTags(existsTagsOne);
//        existsGiftCertificateTwo.setTags(existsTagsTwo);
//        existsGiftCertificateThree.setTags(existsTagsThree);
//
//        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(
//                existsGiftCertificateThree,
//                existsGiftCertificateTwo,
//                existsGiftCertificateOne
//        );
//
//        Set<ColumnName> set = Collections.singleton(ColumnName.CREATE_DATE);
//
//        // Step 3
//        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllSortedWithType(set, SqlSortOperator.DESC);
//
//        // Result
//        assertEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
//
//    @Test
//    public void findAllAndSortLastUpdateDateAndDescPositive() {
//        // Step 1
//        List<GiftCertificate> giftCertificates = Arrays.asList(
//                existsGiftCertificateOne,
//                existsGiftCertificateTwo,
//                existsGiftCertificateThree
//        );
//
//        Mockito.when(giftCertificateDao.findAllBy()).thenReturn(giftCertificates);
//
//        // Step 2
//        existsGiftCertificateOne.setTags(existsTagsOne);
//        existsGiftCertificateTwo.setTags(existsTagsTwo);
//        existsGiftCertificateThree.setTags(existsTagsThree);
//
//        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(
//                existsGiftCertificateThree,
//                existsGiftCertificateTwo,
//                existsGiftCertificateOne
//        );
//
//        Set<ColumnName> set = Collections.singleton(ColumnName.DESCRIPTION);
//
//        // Step 3
//        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllSortedWithType(set, SqlSortOperator.DESC);
//
//        // Result
//        assertEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
//
//    @Test
//    public void findAllAndSortWithoutSortingAndDescPositive() {
//        // Step 1
//        List<GiftCertificate> giftCertificates = Arrays.asList(
//                existsGiftCertificateOne,
//                existsGiftCertificateTwo,
//                existsGiftCertificateThree
//        );
//
//        Mockito.when(giftCertificateDao.findAllBy()).thenReturn(giftCertificates);
//
//        // Step 2
//        existsGiftCertificateOne.setTags(existsTagsOne);
//        existsGiftCertificateTwo.setTags(existsTagsTwo);
//        existsGiftCertificateThree.setTags(existsTagsThree);
//
//        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(
//                existsGiftCertificateThree,
//                existsGiftCertificateTwo,
//                existsGiftCertificateOne
//        );
//
//        Set<ColumnName> set = Collections.singleton(ColumnName.PRICE);
//
//        // Step 3
//        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllSortedWithType(set, SqlSortOperator.DESC);
//
//        // Result
//        assertEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByIdPositive() {
//        long certificateId = 1;
//        Mockito.when(giftCertificateDao.findBy(certificateId)).thenReturn(Optional.of(existsGiftCertificateOne));
//        Mockito.when(tagDao.findByGiftCertificateId(certificateId)).thenReturn(existsTagsOne);
//        existsGiftCertificateOne.setTags(existsTagsOne);
//        GiftCertificate actualGiftCertificate = giftCertificateService.findBy(certificateId);
//        assertEquals(existsGiftCertificateOne, actualGiftCertificate);
//    }
//
//    @Test
//    public void findByIdExceptionPositive() {
//        long nonExistsCertificateId = 100;
//        Mockito.when(giftCertificateDao.findBy(nonExistsCertificateId)).thenThrow(EntityNotFoundException.class);
//        Throwable throwable = assertThrows(EntityNotFoundException.class, () -> giftCertificateService.findBy(nonExistsCertificateId));
//        assertNotNull(throwable);
//    }
//
//    @Test
//    public void findByTagNamePositive() {
//        long tagId = 1;
//        String existingTagName = "epam";
//        Tag epamTag = new Tag(tagId, existingTagName);
//        Optional<Tag> optionalTag = Optional.of(epamTag);
//
//        Mockito.when(tagDao.findByName(existingTagName)).thenReturn(optionalTag);
//
//        List<GiftCertificate> giftCertificates = Collections.singletonList(existsGiftCertificateOne);
//
//        Mockito.when(giftCertificateDao.findByTagId(tagId)).thenReturn(giftCertificates);
//        Mockito.when(tagDao.findByGiftCertificateId(1)).thenReturn(existsTagsOne);
//
//        existsGiftCertificateOne.setTags(existsTagsOne);
//        List<GiftCertificate> expectedGiftCertificates = Collections.singletonList(existsGiftCertificateOne);
//        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findByTagName(existingTagName);
//
//        assertEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByTagNameException() {
//        String nonExistingTagName = "xxxxxxxxx";
//        Mockito.when(tagDao.findByName(nonExistingTagName)).thenThrow(EntityNotFoundException.class);
//        Throwable throwable = assertThrows(EntityNotFoundException.class, () -> giftCertificateService.findByTagName(nonExistingTagName));
//        assertNotNull(throwable);
//    }
//}
