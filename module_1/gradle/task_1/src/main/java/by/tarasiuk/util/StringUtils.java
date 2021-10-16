package by.tarasiuk.util;

import static org.apache.commons.lang3.math.NumberUtils.createDouble;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isPositiveNumber(String str) {
        if(!isCreatable(str)) {
            throw new NumberFormatException("Incorrect value: '" + str + "'.");
        }

        return createDouble(str) > 0;
    }
}
