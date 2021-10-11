package com.epam.esm.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * Tag entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class Tag {
    private long id;

    @NotNull
    @Size(min = 2, max = 50, message = "Tag name length must be from 2 to 5 characters")
    private String name;
}
