package com.library.orderservice.model;

import com.library.orderservice.dto.AddressRequest;
import com.library.orderservice.dto.AddressUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "address")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Address {

    @Id
    private String _id;
    @NotBlank
    private String userId;
    @NotBlank
    private String addressName;
    @NotBlank
    private String cep;
    @NotBlank
    private String street;
    private String adjunct;
    @NotBlank
    private int number;
    @NotBlank
    private String district;
    @NotBlank
    private String city;
    @NotBlank
    private String uf;

    public Address(@Valid AddressRequest addressRequest) {
        this.userId = addressRequest.userID();
        this.addressName = addressRequest.addressName();
        this.cep = addressRequest.cep();
        this.street = addressRequest.street();
        this.adjunct = addressRequest.adjunct();
        this.number = addressRequest.number();
        this.district = addressRequest.district();
        this.city = addressRequest.city();
        this.uf = addressRequest.uf();
    }

    public void update(@Valid AddressUpdate addressUpdate) {

        if (addressUpdate.addressName() != null) {
            this.addressName = addressUpdate.addressName();
        }

        if (addressUpdate.cep() != null) {
            this.cep = addressUpdate.cep();
        }

        if (addressUpdate.street() != null) {
            this.street = addressUpdate.street();
        }

        if (addressUpdate.adjunct() != null) {
            this.adjunct = addressUpdate.adjunct();
        }

        if (addressUpdate.number() != 0) {
            this.number = addressUpdate.number();
        }

        if (addressUpdate.district() != null) {
            this.district = addressUpdate.district();
        }

        if (addressUpdate.city() != null) {
            this.city = addressUpdate.city();
        }

        if (addressUpdate.uf() != null) {
            this.uf = addressUpdate.uf();
        }
    }
}
