package com.epam.esm.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public abstract class AbstractDto<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {
    protected Long id;
}