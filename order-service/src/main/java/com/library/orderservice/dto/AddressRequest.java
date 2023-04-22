package com.library.orderservice.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank
        String addressName,
        @NotBlank
        String userId,
        @NotBlank
        String cep,
        @NotBlank
        String street,
        String adjunct,
        @NotBlank
        String number,
        @NotBlank
        String district,
        @NotBlank
        String city,
        @NotBlank
        String uf
) {
}
