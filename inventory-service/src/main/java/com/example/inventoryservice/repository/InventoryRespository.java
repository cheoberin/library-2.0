package com.example.inventoryservice.repository;

import com.example.inventoryservice.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InventoryRespository extends MongoRepository<Inventory, String> {

    List<Inventory> findByBookIdIn(List<String> bookId);



}
