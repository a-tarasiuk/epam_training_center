//package com.epam.esm.dao.impl;
//
//import com.epam.esm.configuration.EsmConfigurationTest;
//import com.epam.esm.entity.GiftCertificate;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringJUnitConfig(EsmConfigurationTest.class)
//@Sql(scripts = "classpath:database/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = "classpath:database/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//public class GiftCertificateDaoImplTest {
//    private final static Long ID_4 = 4L;
//    private final static Long ID_1 = 1L;
//    private static final String PART_OF_NAME_POSITIVE = "fi";
//    private static final String PART_OF_NAME_NEGATIVE = "xxx";
//    private static final String PART_OF_DESCRIPTION_POSITIVE = "description";
//    private static final String PART_OF_DESCRIPTION_NEGATIVE = "copy";
//    private static final String NAME_CERTIFICATE_1 = "certificate 1";
//    private static final String NAME = "certificate 4";
//    private static final String DESCRIPTION = "description for gift certificate 4";
//    private static final BigDecimal PRICE = BigDecimal.valueOf(1.10);
//    private static final Integer DURATION = 1;
//    private static final LocalDateTime CREATE_DATE = LocalDateTime.of(2021, 10, 04, 17, 05, 53, 889);
//    private static final LocalDateTime LAST_UPDATE_DATE = LocalDateTime.of(2021, 10, 04, 17, 05, 53, 889);
//    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE;
//    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE_FIRST;
//    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE_SECOND;
//    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE_THIRD;
//    private static List<GiftCertificate> EXPECTED_GIFT_CERTIFICATES;
//    private final GiftCertificateDaoImpl giftCertificateDao;
//
//    @Autowired
//    public GiftCertificateDaoImplTest(GiftCertificateDaoImpl giftCertificateDao) {
//        this.giftCertificateDao = giftCertificateDao;
//    }
//
//    @BeforeAll
//    static void init() {
//        // Initialize gift certificate
//        EXPECTED_GIFT_CERTIFICATE = new GiftCertificate();
//        EXPECTED_GIFT_CERTIFICATE.setId(ID_4);
//        EXPECTED_GIFT_CERTIFICATE.setName(NAME);
//        EXPECTED_GIFT_CERTIFICATE.setDescription(DESCRIPTION);
//        EXPECTED_GIFT_CERTIFICATE.setPrice(PRICE);
//        EXPECTED_GIFT_CERTIFICATE.setDuration(DURATION);
//        EXPECTED_GIFT_CERTIFICATE.setCreateDate(CREATE_DATE);
//        EXPECTED_GIFT_CERTIFICATE.setLastUpdateDate(LAST_UPDATE_DATE);
//
//        EXPECTED_GIFT_CERTIFICATE_FIRST = new GiftCertificate(1L, "certificate 1", "description for gift certificate 1", BigDecimal.valueOf(1.10F), 1,
//                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
//                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
//                null);
//
//        EXPECTED_GIFT_CERTIFICATE_SECOND = new GiftCertificate(2L, "certificate 2", "description for gift certificate 2", BigDecimal.valueOf(2.20F), 2,
//                LocalDateTime.of(2021, 10, 02, 17, 05, 53, 889000000),
//                LocalDateTime.of(2021, 10, 02, 17, 05, 53, 889000000),
//                null);
//
//        EXPECTED_GIFT_CERTIFICATE_THIRD = new GiftCertificate(3L, "certificate 3", "description for gift certificate 3", BigDecimal.valueOf(3.30F), 3,
//                LocalDateTime.of(2021, 10, 03, 17, 05, 53, 889000000),
//                LocalDateTime.of(2021, 10, 03, 17, 05, 53, 889000000),
//                null);
//
//        EXPECTED_GIFT_CERTIFICATES = Arrays.asList(
//                EXPECTED_GIFT_CERTIFICATE_FIRST,
//                EXPECTED_GIFT_CERTIFICATE_SECOND,
//                EXPECTED_GIFT_CERTIFICATE_THIRD
//        );
//    }
//
//    @Test
//    public void createPositive() {
//        GiftCertificate actualGiftCertificate = giftCertificateDao.create(EXPECTED_GIFT_CERTIFICATE);
//        assertEquals(EXPECTED_GIFT_CERTIFICATE, actualGiftCertificate);
//    }
//
//    @Test
//    public void findAllPositive() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findAllBy();
//        assertEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByIdPositive() {
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findBy(ID_1);
//        boolean actualResult = optionalGiftCertificate.isPresent();
//        assertTrue(actualResult);
//    }
//
//    @Test
//    public void findByIdNegative() {
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findBy(ID_4);
//        boolean actualResult = optionalGiftCertificate.isPresent();
//        assertFalse(actualResult);
//    }
//
//    @Test
//    public void findByNamePositive() {
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findByName(NAME_CERTIFICATE_1);
//        boolean actualResult = optionalGiftCertificate.isPresent();
//        assertTrue(actualResult);
//    }
//
//    @Test
//    public void findByNameNegative() {
//        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findByName(DESCRIPTION);
//        boolean actualResult = optionalGiftCertificate.isPresent();
//        assertFalse(actualResult);
//    }
//
//    @Test
//    public void findByPartOfNamePositive() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findByPartOfName(PART_OF_NAME_POSITIVE);
//        assertEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByPartOfNameNegative() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findByPartOfName(PART_OF_NAME_NEGATIVE);
//        assertNotEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByPartOfDescriptionPositive() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findByPartOfDescription(PART_OF_DESCRIPTION_POSITIVE);
//        assertEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByPartOfDescriptionNegative() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findByPartOfDescription(PART_OF_DESCRIPTION_NEGATIVE);
//        assertNotEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
//    }
//
//    @Test
//    public void deletePositive() {
//        boolean actualResult = giftCertificateDao.delete(1);
//        assertTrue(actualResult);
//    }
//
//    @Test
//    public void deleteNegative() {
//        boolean actualResult = giftCertificateDao.delete(99);
//        assertFalse(actualResult);
//    }
//
//    @Test
//    public void findByTagIdPositiveVersion1() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findByTagId(1);
//        List<GiftCertificate> expectedGiftCertificates = Collections.singletonList(EXPECTED_GIFT_CERTIFICATE_FIRST);
//        assertEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByTagIdPositiveVersion2() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findByTagId(3);
//        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(EXPECTED_GIFT_CERTIFICATE_FIRST, EXPECTED_GIFT_CERTIFICATE_THIRD);
//        assertEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByTagIdPositiveVersion3() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findByTagId(2);
//        List<GiftCertificate> expectedGiftCertificates = Arrays.asList(EXPECTED_GIFT_CERTIFICATE_FIRST, EXPECTED_GIFT_CERTIFICATE_SECOND);
//        assertEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
//
//    @Test
//    public void findByTagIdNegative() {
//        List<GiftCertificate> actualGiftCertificates = giftCertificateDao.findByTagId(2);
//        List<GiftCertificate> expectedGiftCertificates = Collections.singletonList(EXPECTED_GIFT_CERTIFICATE_FIRST);
//        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
//    }
//}
