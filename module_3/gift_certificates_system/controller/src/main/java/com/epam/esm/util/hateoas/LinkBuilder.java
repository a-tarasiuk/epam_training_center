package com.epam.esm.util.hateoas;

import com.epam.esm.dto.AbstractDto;

import java.util.Set;

public interface LinkBuilder<T extends AbstractDto<T>> {
    void build(T dto);
    void build(Set<T> dtos);
}
