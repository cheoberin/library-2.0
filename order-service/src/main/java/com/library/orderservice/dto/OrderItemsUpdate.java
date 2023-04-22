package com.library.orderservice.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record OrderItemsUpdate(
        @NotBlank
        String _id,
        String bookId,
        String bookName,
        BigDecimal price,
        int quantity
) {
}
