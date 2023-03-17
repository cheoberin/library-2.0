package com.library.bookservice.dto;

import javax.validation.constraints.NotBlank;

public record GenreUpdate(
        @NotBlank String _id,
        String name,
        String description
) {
}
