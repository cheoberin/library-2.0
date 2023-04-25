package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.InventoryDetails;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> bookIds){
        return inventoryService.isInStock(bookIds);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<InventoryDetails> findInventoryByBookId(@PathVariable String bookId){
        return ResponseEntity.ok().body(inventoryService.findByBookId(bookId));
    }

    @PostMapping
    public ResponseEntity<InventoryDetails> createInventory(@RequestBody Inventory inventory,UriComponentsBuilder uriComponentsBuilder){
        InventoryDetails inventoryDetails = inventoryService.create(inventory);
        URI uri = uriComponentsBuilder.path("/api/book/{id}").buildAndExpand(inventoryDetails._id()).toUri();
        return ResponseEntity.created(uri).body(inventoryDetails);

    }

    @PutMapping
    public ResponseEntity<InventoryDetails> updateInventory(@RequestBody Inventory inventory,UriComponentsBuilder uriComponentsBuilder){
        InventoryDetails inventoryDetails = inventoryService.update(inventory);
        URI uri = uriComponentsBuilder.path("/api/book/{id}").buildAndExpand(inventoryDetails._id()).toUri();
        return ResponseEntity.created(uri).body(inventoryDetails);

    }

}
