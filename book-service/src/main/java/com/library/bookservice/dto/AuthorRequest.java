package com.library.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
