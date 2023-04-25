package com.library.orderservice.service;

import com.library.orderservice.model.Address;
import com.library.orderservice.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {

    @Autowired
    private AddressRepository addressRepository;


    public void dbInit(){
        Address address = new Address(null,
                "644427b75e5e4568f74ec1e6",
                "Apt",
                "86071292",
                "Rua Marfao",
                "teste",
                "124",
                "leonori",
                "londrama",
                "sp"
        );

        addressRepository.save(address);

    }


}
