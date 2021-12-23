package com.epam.esm.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractDto<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {
    protected Long id;
}