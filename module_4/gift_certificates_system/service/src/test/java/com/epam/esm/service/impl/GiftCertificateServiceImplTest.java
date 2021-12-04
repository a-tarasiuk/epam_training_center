package com.epam.esm.service.impl;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.GiftCertificateToTagRelation;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.GiftCertificateToTagRelationRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.util.EsmPagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
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
    private GiftCertificateServiceImpl giftCertificateService;

    @BeforeEach
    public void beforeAll() {
        MockitoAnnotations.openMocks(this);
        tagDto = new TagDto();
        tagDto.setId(4L);
        tagDto.setName("abstract tagDto");
    }

    @Test
    public void create() {

    }

    @Test
    public void createPositive() {
        GiftCertificate giftCertificate = new GiftCertificate(5L, "certificate", "description for gift certificate 4", BigDecimal.valueOf(1.10F), 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000));

        Tag tag = new Tag();
        tag.setId(6L);
        tag.setName("tag");

        Mockito.when(certificateRepository.findByName("certificate")).thenReturn(Optional.empty());
        Mockito.when(certificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        Mockito.when(tagRepository.findByName("abstract tagDto")).thenReturn(Optional.of(tag));
        Mockito.when(relationRepository.findByGiftCertificateAndTag(giftCertificate, tag)).thenReturn(Optional.empty());
        GiftCertificateToTagRelation relation = new GiftCertificateToTagRelation(giftCertificate, tag);
        Mockito.when(relationRepository.save(relation)).thenReturn(relation);
        assertNotNull(giftCertificateService.create(modelMapper.map(giftCertificate, GiftCertificateDto.class)));
    }

    @Test
    public void findAllPositive() {
        List<GiftCertificateDto> giftCertificates = Arrays.asList(
                existsGiftCertificateOne,
                existsGiftCertificateTwo,
                existsGiftCertificateThree
        );

        Mockito.when(certificateRepository.findAll()).thenReturn(giftCertificates.stream()
                .map(dto -> modelMapper.map(dto, GiftCertificate.class))
                .collect(Collectors.toList()));

        existsGiftCertificateOne.setTags(existsTagsOne);
        existsGiftCertificateTwo.setTags(existsTagsTwo);
        existsGiftCertificateThree.setTags(existsTagsThree);

        assertEquals(giftCertificateService.findAll(new EsmPagination()).getTotalElements(), 3);
    }

    @Test
    public void findByIdPositive() {
        long certificateId = 5;

        GiftCertificate giftCertificate = new GiftCertificate(5L, "certificate", "description for gift certificate 4", BigDecimal.valueOf(1.10F), 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000));

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("name");

        Set<Tag> tags = Collections.singleton(tag);

        Mockito.when(certificateRepository.findById(certificateId)).thenReturn(Optional.of(giftCertificate));
        Mockito.when(tagRepository.findAllByGiftCertificate(giftCertificate)).thenReturn(tags);
        GiftCertificateDto actualGiftCertificate = giftCertificateService.findById(certificateId);
        assertEquals(modelMapper.map(giftCertificate, GiftCertificateDto.class), actualGiftCertificate);
    }
}
