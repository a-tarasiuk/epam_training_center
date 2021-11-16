package com.epam.esm.util.hateoas;

import com.epam.esm.dto.AbstractDto;

import java.util.Set;

public interface LinkBuilder<T extends AbstractDto<T>> {
    T build(T dto);
    Set<T> build(Set<T> dtos);
}
