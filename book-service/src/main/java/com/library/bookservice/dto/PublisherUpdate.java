package com.library.bookservice.dto;

import javax.validation.constraints.NotBlank;

public record PublisherUpdate(
        @NotBlank String _id,
        String name,
        String description
) {
}
