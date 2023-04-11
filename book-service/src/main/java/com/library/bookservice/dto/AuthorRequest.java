package com.library.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AuthorRequest(
        @NotBlank
        String name,
        @NotNull
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate birthDate,
        @NotBlank
        String nationality,
        @NotBlank
        String description
) {
}
