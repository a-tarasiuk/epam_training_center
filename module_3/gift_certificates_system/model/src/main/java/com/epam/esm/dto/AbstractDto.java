package com.epam.esm.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Setter
@Getter
public abstract class AbstractDto<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> implements Serializable {
    Long id;
}
