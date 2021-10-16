package by.tarasiuk.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest {
    private static final String CORRECT_VALUE = "10";
    private static final String INCORRECT_VALUE = "-10";
    private static final String INCORRECT_STRING = "Incorrect";

    @Test
    void isPositiveNumberTrue() {
        assertTrue(StringUtils.isPositiveNumber(CORRECT_VALUE));
    }

    @Test
    void isPositiveNumberFalseOne() {
        assertFalse(StringUtils.isPositiveNumber(INCORRECT_VALUE));
    }

    @Test
    void isPositiveNumberException() {
        assertThrows(NumberFormatException.class, () -> {
            StringUtils.isPositiveNumber(INCORRECT_STRING);
        });
    }
}
