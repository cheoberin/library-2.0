package com.library.orderservice.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressUpdate(
        @NotBlank
        String _id,
        String userId,
        String addressName,
        String cep,
        String street,
        String adjunct,
        int number,
        String district,
        String city,
        String uf
) {
}
