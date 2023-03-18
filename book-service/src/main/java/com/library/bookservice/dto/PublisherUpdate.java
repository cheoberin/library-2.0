package com.library.bookservice.dto;

import jakarta.validation.constraints.NotBlank;

public record PublisherUpdate(
        @NotBlank String _id,
        String name,
        String description
) {
}
