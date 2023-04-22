package com.library.orderservice.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank
        String userID,
        @NotBlank
        String addressName,
        @NotBlank
        String cep,
        @NotBlank
        String street,
        String adjunct,
        @NotBlank
        int number,
        @NotBlank
        String district,
        @NotBlank
        String city,
        @NotBlank
        String uf
) {
}
