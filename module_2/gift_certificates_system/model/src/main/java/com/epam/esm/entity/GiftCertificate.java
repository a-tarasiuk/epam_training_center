package com.epam.esm.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Gift certificate entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {
    private Long id;

    @NotEmpty(message = "The name cannot be empty")
    @Size(min = 2, max = 50, message = "Name length 2 to 50 letters")
    private String name;

    @NotEmpty(message = "The description cannot be empty")
    @Size(min = 2, max = 200, message = "Description length 2 to 200 characters")
    private String description;

    @Positive
    private Float price;

    @Positive
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    @NotEmpty(message = "The tag list cannot be empty")
    private List<@Valid Tag> tags;
}
