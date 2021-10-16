package by.tarasiuk.util;

import java.util.Arrays;

public class Utils {
     public static boolean isAllPositiveNumbers(String... str) {
         return Arrays.stream(str)
                .allMatch(StringUtils::isPositiveNumber);
    }
}
