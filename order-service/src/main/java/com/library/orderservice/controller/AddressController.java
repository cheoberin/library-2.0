package com.library.orderservice.controller;

import com.library.orderservice.dto.AddressDetails;
import com.library.orderservice.dto.AddressRequest;
import com.library.orderservice.dto.AddressResponse;
import com.library.orderservice.dto.AddressUpdate;
import com.library.orderservice.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid AddressRequest addressRequest, UriComponentsBuilder uriComponentsBuilder) {
        AddressDetails addressDetails = addressService.save(addressRequest);
        URI uri = uriComponentsBuilder.path("/api/address/{id}").buildAndExpand(addressDetails._id()).toUri();
        return ResponseEntity.created(uri).body(addressDetails);
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<AddressResponse> addressResponseList = addressService.findAll();
        return ResponseEntity.ok().body(addressResponseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        AddressDetails addressDetails = addressService.findById(id);
        return ResponseEntity.ok().body(addressDetails);
    }

    @PutMapping
    public ResponseEntity udpate(@RequestBody @Valid AddressUpdate addressUpdate) {
        AddressDetails addressDetails = addressService.update(addressUpdate);
        return ResponseEntity.ok().body(addressDetails);
    }

}
