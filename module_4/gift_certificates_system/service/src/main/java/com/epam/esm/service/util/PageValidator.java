package com.epam.esm.service.util;

import com.epam.esm.repository.exception.IncorrectArgumentException;
import com.epam.esm.repository.util.EsmPagination;
import org.springframework.data.domain.Page;

import static com.epam.esm.model.util.MessagePropertyKey.EXCEPTION_ESM_PAGINATION_PAGE_OUT_OF_RANGE;

public final class PageValidator {
    private PageValidator() {
    }

    public static void validate(EsmPagination pagination, Page<?> page) {
        long countMaxPages = page.getTotalPages();

        if (pagination.getPage() > countMaxPages) {
            throw new IncorrectArgumentException(EXCEPTION_ESM_PAGINATION_PAGE_OUT_OF_RANGE, countMaxPages);
        }
    }
}
