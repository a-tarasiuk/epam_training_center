package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tag entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private long id;
    private String name;
}