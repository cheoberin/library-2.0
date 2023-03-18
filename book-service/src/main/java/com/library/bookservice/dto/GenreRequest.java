package com.library.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;

public record GenreRequest(@NotBlank @Indexed(unique = true) String name, @NotBlank String description) {
}
