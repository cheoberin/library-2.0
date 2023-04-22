package com.library.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotBlank
        String customerId,
        @NotBlank
        String customerName,
        @NotBlank
        String status,
        @NotBlank
        String phone,
        @NotNull
        String addressId,
        @Valid
        @NotNull
        List<OrderItemsDto> orderItemsList
) {
}
