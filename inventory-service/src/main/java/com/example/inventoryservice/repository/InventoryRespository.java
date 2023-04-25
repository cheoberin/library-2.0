package com.example.inventoryservice.repository;

import com.example.inventoryservice.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRespository extends MongoRepository<Inventory, String> {

    List<Inventory> findByBookIdIn(List<String> bookId);

    Optional<Inventory> findInventoryByBookId(String bookId);


}
