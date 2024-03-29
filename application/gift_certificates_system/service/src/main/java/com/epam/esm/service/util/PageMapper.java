package com.epam.esm.service.util;

import com.epam.esm.repository.util.EsmPagination;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class PageMapper {
    private final ModelMapper modelMapper;

    public PageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T, D> Page<D> map(Page<T> source, Class<D> destinationType) {
        List<D> list = source.stream()
                .map(o -> modelMapper.map(o, destinationType))
                .collect(Collectors.toList());

        return map(list);
    }

    public Pageable map(EsmPagination pagination) {
        return PageRequest.of((int) pagination.getPage(), pagination.getSize());
    }

    private <D> Page<D> map(List<D> source) {
        return new PageImpl<>(source);
    }
}
