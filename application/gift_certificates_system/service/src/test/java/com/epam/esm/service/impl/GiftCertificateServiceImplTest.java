package com.epam.esm.service.impl;

<<<<<<< HEAD
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.GiftCertificateToTagRelationDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.util.ColumnName;
import com.epam.esm.util.SqlSortOperator;
=======
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.GiftCertificateToTagRelation;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateToTagRelationRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.util.EsmPagination;
>>>>>>> module_6
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
<<<<<<< HEAD
=======
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> module_6

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
<<<<<<< HEAD
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GiftCertificateServiceImplTest {
    @Mock
    private static GiftCertificateDaoImpl giftCertificateDao;
    @Mock
    private static TagDaoImpl tagDao;
    @Mock
    private static GiftCertificateToTagRelationDaoImpl relationDao;

    private static Tag tag;
    private static GiftCertificate existsGiftCertificateOne;
    private static GiftCertificate existsGiftCertificateTwo;
    private static GiftCertificate existsGiftCertificateThree;
    private static Set<Tag> existsTagsOne;
    private static Set<Tag> existsTagsTwo;
    private static Set<Tag> existsTagsThree;
=======
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GiftCertificateServiceImplTest {
    @Mock
    private static GiftCertificateRepository certificateRepository;
    @Mock
    private static TagRepository tagRepository;
    @Mock
    private static GiftCertificateToTagRelationRepository relationRepository;

    private static TagDto tagDto;
    private static GiftCertificateDto existsGiftCertificateOne;
    private static GiftCertificateDto existsGiftCertificateTwo;
    private static GiftCertificateDto existsGiftCertificateThree;
    private static Set<TagDto> existsTagsOne;
    private static Set<TagDto> existsTagsTwo;
    private static Set<TagDto> existsTagsThree;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
>>>>>>> module_6
    private GiftCertificateServiceImpl giftCertificateService;

    @BeforeEach
    public void beforeAll() {
        MockitoAnnotations.openMocks(this);
<<<<<<< HEAD
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDao, tagDao, relationDao);
        tag = new Tag(4, "abstract tag");

        // Initialization gift certificates
        existsGiftCertificateOne = new GiftCertificate(1L, "certificate 1", "description for gift certificate 1", BigDecimal.valueOf(1.10F), 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                null);
        existsGiftCertificateTwo = new GiftCertificate(2L, "certificate 2", "description for gift certificate 2", BigDecimal.valueOf(2.20F), 2,
                LocalDateTime.of(2021, 10, 02, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 02, 17, 05, 53, 889000000),
                null);
        existsGiftCertificateThree = new GiftCertificate(3L, "certificate 3", "description for gift certificate 3", BigDecimal.valueOf(3.30F), 3,
                LocalDateTime.of(2021, 10, 03, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 03, 17, 05, 53, 889000000),
                null);

        // Initialization tags
        existsTagsOne = new HashSet<Tag>(Arrays.asList(
                new Tag(1, "epam"),
                new Tag(2, "gift"),
                new Tag(3, "gym")
        ));
        existsTagsTwo = Collections.singleton(new Tag(2, "gift"));
        existsTagsThree = Collections.singleton(new Tag(3, "gym"));
=======
        tagDto = new TagDto();
        tagDto.setId(4L);
        tagDto.setName("abstract tagDto");
    }

    @Test
    public void create() {

>>>>>>> module_6
    }

    @Test
    public void createPositive() {
<<<<<<< HEAD
        GiftCertificate giftCertificate = new GiftCertificate(4L, "certificate", "description for gift certificate 4", BigDecimal.valueOf(1.10F), 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                Collections.singleton(tag));

        Mockito.when(giftCertificateDao.findByName("certificate")).thenReturn(Optional.empty());
        Mockito.when(giftCertificateDao.create(giftCertificate)).thenReturn(giftCertificate);
        Mockito.when(tagDao.findByName("abstract tag")).thenReturn(Optional.of(tag));
        Mockito.when(relationDao.find(1, 1)).thenReturn(Optional.empty());
        Mockito.when(relationDao.create(1, 1)).thenReturn(true);
        assertNotNull(giftCertificateService.create(giftCertificate));
=======
        GiftCertificate giftCertificate = new GiftCertificate(5L, "certificate", "description for gift certificate 4", BigDecimal.valueOf(1.10F), 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000));

        Tag tag = new Tag();
        tag.setId(6L);
        tag.setName("tag");

        Mockito.when(certificateRepository.findByName("certificate")).thenReturn(Optional.empty());
        Mockito.when(certificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        Mockito.when(tagRepository.findByName("abstract tagDto")).thenReturn(Optional.of(tag));
        Mockito.when(relationRepository.findByGiftCertificateIdAndTagId(giftCertificate.getId(), tag.getId())).thenReturn(Optional.empty());
        GiftCertificateToTagRelation relation = new GiftCertificateToTagRelation(giftCertificate, tag);
        Mockito.when(relationRepository.save(relation)).thenReturn(relation);
        assertNotNull(giftCertificateService.create(modelMapper.map(giftCertificate, GiftCertificateDto.class)));
>>>>>>> module_6
    }

    @Test
    public void findAllPositive() {
<<<<<<< HEAD
        List<GiftCertificate> giftCertificates = Arrays.asList(
=======
        List<GiftCertificateDto> giftCertificates = Arrays.asList(
>>>>>>> module_6
                existsGiftCertificateOne,
                existsGiftCertificateTwo,
                existsGiftCertificateThree
        );

<<<<<<< HEAD
        Mockito.when(giftCertificateDao.findAll()).thenReturn(giftCertificates);
=======
        Mockito.when(certificateRepository.findAll()).thenReturn(giftCertificates.stream()
                .map(dto -> modelMapper.map(dto, GiftCertificate.class))
                .collect(Collectors.toList()));
>>>>>>> module_6

        existsGiftCertificateOne.setTags(existsTagsOne);
        existsGiftCertificateTwo.setTags(existsTagsTwo);
        existsGiftCertificateThree.setTags(existsTagsThree);

<<<<<<< HEAD
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

        Set<ColumnName> set = Collections.singleton(ColumnName.NAME);

        // Step 3
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllSortedWithType(set, SqlSortOperator.DESC);

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

        Set<ColumnName> set = Collections.singleton(ColumnName.CREATE_DATE);

        // Step 3
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllSortedWithType(set, SqlSortOperator.DESC);

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

        Set<ColumnName> set = Collections.singleton(ColumnName.DESCRIPTION);

        // Step 3
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllSortedWithType(set, SqlSortOperator.DESC);

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

        Set<ColumnName> set = Collections.singleton(ColumnName.PRICE);

        // Step 3
        List<GiftCertificate> actualGiftCertificates = giftCertificateService.findAllSortedWithType(set, SqlSortOperator.DESC);

        // Result
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
=======
        assertEquals(giftCertificateService.findAll(new EsmPagination()).getTotalElements(), 3);
>>>>>>> module_6
    }

    @Test
    public void findByIdPositive() {
<<<<<<< HEAD
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
=======
        long certificateId = 5;

        GiftCertificate giftCertificate = new GiftCertificate(5L, "certificate", "description for gift certificate 4", BigDecimal.valueOf(1.10F), 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000));

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("name");

        Set<Tag> tags = Collections.singleton(tag);

        Mockito.when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(giftCertificate));
        Mockito.when(tagRepository.findAllByGiftCertificateId(giftCertificate.getId())).thenReturn(tags);
        GiftCertificateDto actualGiftCertificate = giftCertificateService.findById(certificateId);
        assertEquals(modelMapper.map(giftCertificate, GiftCertificateDto.class), actualGiftCertificate);
>>>>>>> module_6
    }
}
