package com.example.inventoryservice.dto;

import com.example.inventoryservice.model.Inventory;

public record InventoryDetails(String _id, String bookId, int quantity) {

   public InventoryDetails(Inventory inventory){
        this(inventory.get_id(), inventory.getBookId(), inventory.getQuantity());
    }
}