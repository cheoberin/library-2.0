package com.library.orderservice.dto;

import com.library.orderservice.model.Address;

public record AddressResponse(String _id, String userId, String addressName) {

    public AddressResponse(Address address) {
        this(address.get_id(), address.getUserId(), address.getAddressName());
    }

}
