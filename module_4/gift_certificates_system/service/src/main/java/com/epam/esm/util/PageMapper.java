package com.epam.esm.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public final class PageMapper {
    @Autowired
    private ModelMapper modelMapper;

    public <T, D> Page<D> map(Page<T> source, Class<D> destinationType) {
        List<D> list = source.stream()
                .map(o -> modelMapper.map(o, destinationType))
                .collect(Collectors.toList());

        return map(list);
    }

    public Pageable map(EsmPagination pagination) {
        return PageRequest.of(pagination.getPage(), pagination.getSize());
    }

    private <D> Page<D> map(List<D> source) {
        return new PageImpl<>(source);
    }
}