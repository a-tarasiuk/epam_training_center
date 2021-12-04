package com.epam.esm.repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.repository.configuration.RepositoryConfigurationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = RepositoryConfigurationTest.class)
@Sql(scripts = "classpath:database/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class GiftCertificateRepositoryTest {
    private static GiftCertificate GIFT_CERTIFICATE;
    private final GiftCertificateRepository certificateRepository;

    @Autowired
    public GiftCertificateRepositoryTest(GiftCertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @BeforeAll
    static void init() {
        GIFT_CERTIFICATE = new GiftCertificate(4L, "certificate 9", "description for gift certificate 1", BigDecimal.valueOf(1.10F), 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000));
    }

    @Test
    public void createPositive() {
        GiftCertificate actualGiftCertificate = certificateRepository.save(GIFT_CERTIFICATE);
        assertEquals(actualGiftCertificate.getId(), 4L);
    }

    @Test
    public void createNegative() {
        GiftCertificate actualGiftCertificate = certificateRepository.save(GIFT_CERTIFICATE);
        assertNotEquals(actualGiftCertificate.getId(), 999L);
    }

    @Test
    public void findByNamePositive() {
        assertTrue(certificateRepository.findByName("certificate 1").isPresent());
    }

    @Test
    public void findByNameNegative() {
        assertTrue(certificateRepository.findByName("999").isPresent());
    }
}
