package com.library.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemsDto(
        @NotBlank
        String bookId,
        @NotBlank
        String bookName,
        @NotNull
        BigDecimal price,
        @NotNull
        int quantity
) {
}
