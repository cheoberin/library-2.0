package com.library.orderservice.dto;

import com.library.orderservice.model.Address;

public record AddressDetails(String _id, String userId, String addressName, String cep, String street, String adjunct,
                             String number, String district, String city, String uf) {

    public AddressDetails(Address address) {
        this(address.get_id(), address.getUserId(), address.getAddressName(), address.getCep(), address.getStreet(), address.getAdjunct(), address.getNumber(), address.getDistrict(), address.getCity(), address.getUf());
    }

}
