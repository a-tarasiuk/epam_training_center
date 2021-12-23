package com.epam.esm.repository.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.Serializable;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_ESM_PAGINATION_PAGE_INCORRECT_VALUE;
import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_ESM_PAGINATION_SIZE_INCORRECT_VALUE;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class EsmPagination implements Serializable {
    private static final long MIN_PAGE_INDEX = 0;
    private static final int DEFAULT_ELEMENTS_ON_PAGE = 10;

    @Min(value = MIN_PAGE_INDEX, message = EXCEPTION_ESM_PAGINATION_PAGE_INCORRECT_VALUE)
    private long page;

    @Positive(message = EXCEPTION_ESM_PAGINATION_SIZE_INCORRECT_VALUE)
    private int size;

    public EsmPagination() {
        this.page = MIN_PAGE_INDEX;
        this.size = DEFAULT_ELEMENTS_ON_PAGE;
    }
}
