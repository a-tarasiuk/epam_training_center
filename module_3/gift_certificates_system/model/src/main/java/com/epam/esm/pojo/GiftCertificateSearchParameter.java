package com.epam.esm.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Setter
@Getter
@RequiredArgsConstructor
@ToString
public class GiftCertificateSearchParameter {
    private String keyword;
    private Set<String> tagNames;
    private Set<String> sortBy;
}
