package com.epam.esm.dao.impl;

import com.epam.esm.configuration.EsmConfigurationTest;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.EsmPagination;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = EsmConfigurationTest.class)
@Sql(scripts = "classpath:database/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GiftCertificateDaoImplTest {
    private final static Long ID_4 = 4L;
    private final static Long ID_1 = 1L;
    private static final String PART_OF_NAME_POSITIVE = "fi";
    private static final String NAME_CERTIFICATE_1 = "certificate 1";
    private static final String NAME = "certificate 4";
    private static final String DESCRIPTION = "description for gift certificate 4";
    private static final BigDecimal PRICE = BigDecimal.valueOf(1.10);
    private static final Integer DURATION = 1;
    private static final LocalDateTime CREATE_DATE = LocalDateTime.of(2021, 10, 04, 17, 05, 53, 889);
    private static final LocalDateTime LAST_UPDATE_DATE = LocalDateTime.of(2021, 10, 04, 17, 05, 53, 889);
    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE;
    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE_FIRST;
    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE_SECOND;
    private static GiftCertificate EXPECTED_GIFT_CERTIFICATE_THIRD;
    private static Set<GiftCertificate> EXPECTED_GIFT_CERTIFICATES;
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @BeforeAll
    static void init() {
        // Initialize gift certificate
        EXPECTED_GIFT_CERTIFICATE = new GiftCertificate();
        EXPECTED_GIFT_CERTIFICATE.setId(ID_4);
        EXPECTED_GIFT_CERTIFICATE.setName(NAME);
        EXPECTED_GIFT_CERTIFICATE.setDescription(DESCRIPTION);
        EXPECTED_GIFT_CERTIFICATE.setPrice(PRICE);
        EXPECTED_GIFT_CERTIFICATE.setDuration(DURATION);
        EXPECTED_GIFT_CERTIFICATE.setCreateDate(CREATE_DATE);
        EXPECTED_GIFT_CERTIFICATE.setLastUpdateDate(LAST_UPDATE_DATE);

        EXPECTED_GIFT_CERTIFICATE_FIRST = new GiftCertificate(1L, "certificate 1", "description for gift certificate 1", BigDecimal.valueOf(1.10F), 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000));

        EXPECTED_GIFT_CERTIFICATE_SECOND = new GiftCertificate(2L, "certificate 2", "description for gift certificate 2", BigDecimal.valueOf(2.20F), 2,
                LocalDateTime.of(2021, 10, 02, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 02, 17, 05, 53, 889000000));

        EXPECTED_GIFT_CERTIFICATE_THIRD = new GiftCertificate(3L, "certificate 3", "description for gift certificate 3", BigDecimal.valueOf(3.30F), 3,
                LocalDateTime.of(2021, 10, 03, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 03, 17, 05, 53, 889000000));

        EXPECTED_GIFT_CERTIFICATES = Stream.of(
                EXPECTED_GIFT_CERTIFICATE_FIRST,
                EXPECTED_GIFT_CERTIFICATE_SECOND,
                EXPECTED_GIFT_CERTIFICATE_THIRD).collect(Collectors.toSet());
    }

    @Test
    public void createPositive() {
        GiftCertificate actualGiftCertificate = giftCertificateDao.create(EXPECTED_GIFT_CERTIFICATE);
        assertEquals(EXPECTED_GIFT_CERTIFICATE, actualGiftCertificate);
    }

    @Test
    @Order(1)
    public void findAllPositive() {
        Set<GiftCertificate> actualGiftCertificates = giftCertificateDao.findAll(new EsmPagination(1, 1), GiftCertificate.class);
        assertEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
    }

    @Test
    @Order(2)
    public void deletePositive() {
        giftCertificateDao.delete(EXPECTED_GIFT_CERTIFICATE_FIRST);
    }

    @Test
    @Order(3)
    public void deleteNegative() {
        giftCertificateDao.delete(EXPECTED_GIFT_CERTIFICATE_FIRST);
    }

    @Test
    public void findByIdPositive() {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(ID_1);
        boolean actualResult = optionalGiftCertificate.isPresent();
        assertTrue(actualResult);
    }

    @Test
    public void findByIdNegative() {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(ID_4);
        boolean actualResult = optionalGiftCertificate.isPresent();
        assertFalse(actualResult);
    }

    @Test
    public void findByNamePositive() {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findByName(NAME_CERTIFICATE_1);
        boolean actualResult = optionalGiftCertificate.isPresent();
        assertTrue(actualResult);
    }

    @Test
    public void findByNameNegative() {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findByName(DESCRIPTION);
        boolean actualResult = optionalGiftCertificate.isPresent();
        assertFalse(actualResult);
    }

    @Test
    public void findByKeywordPositive() {
        Set<GiftCertificate> actualGiftCertificates = giftCertificateDao.findBy(PART_OF_NAME_POSITIVE);
        assertEquals(EXPECTED_GIFT_CERTIFICATES, actualGiftCertificates);
    }
}
