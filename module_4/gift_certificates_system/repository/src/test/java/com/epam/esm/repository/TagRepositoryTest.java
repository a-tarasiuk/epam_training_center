package com.epam.esm.repository;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.configuration.RepositoryConfigurationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {RepositoryConfigurationTest.class})
@Sql(scripts = "classpath:database/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:database/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @Test
    public void savePositive() {
        Tag tag = new Tag(10L, "Name");
        tagRepository.save(tag);
        assertNotNull(tag.getId());
    }

    @Test
    public void findByIdPositive() {
        assertTrue(tagRepository.findById(1L).isPresent());
    }

    @Test
    public void findByIdNegative() {
        assertFalse(tagRepository.findById(999L).isPresent());
    }

    @Test
    public void findByNamePositive() {
        assertTrue(tagRepository.findByName("certificate 1").isPresent());
    }

    @Test
    public void findByNameNegative() {
        assertFalse(tagRepository.findByName("999").isPresent());
    }

    @Test
    public void findAllPositive() {
        assertEquals(tagRepository.findAll().size(), 3);
    }

    @Test
    public void findAllNegative() {
        assertEquals(tagRepository.findAll().size(), 999);
    }

    @Test
    public void findAllByGiftCertificatePositive() {
        GiftCertificate certificate = new GiftCertificate(1L, "certificate 1", "description for gift certificate 1", BigDecimal.valueOf(1.10F), 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000));
        assertEquals(tagRepository.findAllByGiftCertificate(certificate).size(), 3);
    }

    @Test
    public void findAllByGiftCertificateNegative() {
        GiftCertificate certificate = new GiftCertificate(1L, "certificate 1", "description for gift certificate 1", BigDecimal.valueOf(1.10F), 1,
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000),
                LocalDateTime.of(2021, 10, 01, 17, 05, 53, 889000000));
        assertEquals(tagRepository.findAllByGiftCertificate(certificate).size(), 999);
    }
}
