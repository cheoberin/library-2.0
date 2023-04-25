package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryDetails;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRespository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRespository inventoryRespository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> bookIds){
        log.info("chegou até aqui : {}",bookIds);
        return inventoryRespository.findByBookIdIn(bookIds)
                .stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .bookId(inventory.getBookId())
                            .isInStock(inventory.getQuantity()> 0)
                            .build()
                ).toList();

    }

    public InventoryDetails findByBookId(String bookId){
        return new InventoryDetails(inventoryRespository.findInventoryByBookId(bookId).orElseThrow(()-> new NotFoundException("Object not found")));
    }

    public InventoryDetails create(Inventory inventory){
        return new InventoryDetails(inventoryRespository.save(inventory));
    }

    public InventoryDetails update(Inventory inventory){
        return new InventoryDetails(inventoryRespository.save(inventory));
    }

}
