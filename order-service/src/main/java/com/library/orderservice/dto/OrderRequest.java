package com.library.orderservice.dto;

import com.library.orderservice.model.Address;
import com.library.orderservice.model.OrderBook;
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
        String phone,
        @NotNull
        @Valid
        Address address,
        @Valid
        @NotNull
        List<OrderBook> orderBookList
) {
}
