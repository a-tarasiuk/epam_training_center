package com.epam.esm.util.pagination;

import com.epam.esm.util.MessagePropertyKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class EsmPagination implements Serializable {
    private static final int MIN_PAGE_INDEX = 1;
    private static final int DEFAULT_ELEMENTS_ON_PAGE = 10;

    @Min(value = MIN_PAGE_INDEX, message = MessagePropertyKey.EXCEPTION_ESM_PAGINATION_PAGE_INCORRECT_VALUE)
    private int page;

    @Positive(message = MessagePropertyKey.EXCEPTION_ESM_PAGINATION_SIZE_INCORRECT_VALUE)
    private int size;

    public EsmPagination() {
        this.page = MIN_PAGE_INDEX;
        this.size = DEFAULT_ELEMENTS_ON_PAGE;
    }
}
