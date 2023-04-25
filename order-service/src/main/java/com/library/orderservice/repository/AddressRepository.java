package com.library.orderservice.repository;

import com.library.orderservice.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

    Optional<List<Address>> findAddressesByUserId(String id);
}
