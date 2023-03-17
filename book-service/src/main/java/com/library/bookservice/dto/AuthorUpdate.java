package com.library.bookservice.dto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public record AuthorUpdate(
        @NotBlank String _id,
        String name,
        LocalDate birthDate,
        String nationality,
        String description
) {
}
