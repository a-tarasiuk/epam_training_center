package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GiftCertificateValidatorTest {
    static GiftCertificate gc;
    static GiftCertificate gcInvalid;

    @BeforeEach
    public void beforeAll() {
        gc = new GiftCertificate();
        gc.setName("Epam");
        gc.setDescription("Description");
        gc.setPrice(99F);
        gc.setDuration(10);
    }

    @Test
    public void isValidPositive() {
        boolean result = GiftCertificateValidator.isValid(gc);
        assertTrue(result);
    }

    @Test
    public void isValidNegative1() {
        gc.setName("222");
        boolean result = GiftCertificateValidator.isValid(gc);
        assertFalse(result);
    }

    @Test
    public void isValidNegative2() {
        gc.setName("d");
        boolean result = GiftCertificateValidator.isValid(gc);
        assertFalse(result);
    }

    @Test
    public void isValidNegative3() {
        gc.setDescription("2");
        boolean result = GiftCertificateValidator.isValid(gc);
        assertFalse(result);
    }

    @Test
    public void isValidNegative4() {
        gc.setPrice(99999999999F);
        boolean result = GiftCertificateValidator.isValid(gc);
        assertFalse(result);
    }

    @Test
    public void isValidNegative5() {
        gc.setPrice(99.222F);
        boolean result = GiftCertificateValidator.isValid(gc);
        assertFalse(result);
    }

    @Test
    public void isValidNegative6() {
        gc.setDuration(370);
        boolean result = GiftCertificateValidator.isValid(gc);
        assertFalse(result);
    }
}
