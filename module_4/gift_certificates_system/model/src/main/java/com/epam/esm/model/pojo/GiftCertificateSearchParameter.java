package com.epam.esm.model.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Setter
@Getter
@RequiredArgsConstructor
@ToString
public class GiftCertificateSearchParameter {
    private String keyword;
    private Set<String> tagNames;
    private Set<String> sortBy;

    public boolean isEmptyAllFields() {
        return Stream.of(this.keyword, this.tagNames, this.sortBy)
                .allMatch(Objects::isNull);
    }
}
