package com.library.bookservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record AuthorRequest(
        @NotBlank
        String name,
        @NotNull
        LocalDate birthDate,
        @NotBlank
        String nationality,
        @NotBlank
        String description
) {
}
