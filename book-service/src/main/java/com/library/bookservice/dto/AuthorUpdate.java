package com.library.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AuthorUpdate(
        @NotBlank String _id,
        String name,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate birthDate,
        String nationality,
        String description
) {
}
