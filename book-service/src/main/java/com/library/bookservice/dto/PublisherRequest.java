package com.library.bookservice.dto;

import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;

public record PublisherRequest(@NotBlank @Indexed(unique = true) String name, @NotBlank String description) {
}
