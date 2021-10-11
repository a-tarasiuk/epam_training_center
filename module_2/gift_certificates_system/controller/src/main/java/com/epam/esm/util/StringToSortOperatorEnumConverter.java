package com.epam.esm.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;

/**
 * String to sort operator enum converter.
 *
 * @see com.epam.esm.util.SortOperator
 */
public class StringToSortOperatorEnumConverter implements Converter<String, SortOperator> {
    /**
     * Converting string to sort operator enum.
     *
     * @param source - String.
     * @return - Sort operator enum.
     */
    @Override
    public SortOperator convert(String source) {
        SortOperator sortOperator;

        if (source == null) {
            sortOperator = SortOperator.WITHOUT_SORTING;
        } else {
            sortOperator = Arrays.stream(SortOperator.values())
                    .filter(e -> e.name().equalsIgnoreCase(source)).findFirst().orElse(SortOperator.ASC);
        }

        return sortOperator;
    }
}
