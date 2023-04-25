package com.library.orderservice.service;

import com.library.orderservice.dto.AddressDetails;
import com.library.orderservice.dto.AddressRequest;
import com.library.orderservice.dto.AddressResponse;
import com.library.orderservice.dto.AddressUpdate;
import com.library.orderservice.exception.NotFoundException;
import com.library.orderservice.model.Address;
import com.library.orderservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressDetails save(AddressRequest addressRequest) {
        Address address = new Address(addressRequest);
        AddressDetails addressDetails = new AddressDetails(addressRepository.save(address));
        log.info("Address saved: {} - {}, {}", addressDetails._id(), addressDetails.cep(), addressDetails.number());

        return addressDetails;

    }

    public List<AddressResponse> findAll() {
        List<Address> addresses = addressRepository.findAll();
        log.info("Address list returned");
        return addresses.stream().map(AddressResponse::new).toList();
    }

    public void delete(String id) {
        findById(id);
        addressRepository.deleteById(id);
        log.warn("Address deleted, id: " + id);
    }

    public AddressDetails findById(String id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Object not found: + " + id + ", type: " + Address.class.getName()));
        log.info("Address returned: {} - {}", address.get_id(), address.getAddressName());
        return new AddressDetails(address);
    }

    public AddressDetails update(AddressUpdate addressUpdate) {
        Address address = addressRepository.findById(addressUpdate._id()).orElseThrow(() -> new NotFoundException("Object not found: + " + addressUpdate._id() + ", type: " + Address.class.getName()));
        address.update(addressUpdate);
        addressRepository.save(address);
        log.info("Address updated, id: " + address.get_id());
        return new AddressDetails(address);
    }

    public List<AddressDetails> findByUserId(String id){
        List<Address> addresses = addressRepository.findAddressesByUserId(id).orElseThrow(()-> new NotFoundException("Object not found: " + id));
        return addresses.stream().map(AddressDetails::new).toList();
    }

}
