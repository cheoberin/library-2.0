package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.repository.InventoryRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRespository inventoryRespository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> bookIds){

        return inventoryRespository.findByBookIdIn(bookIds)
                .stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .bookId(inventory.getBookId())
                            .isInStock(inventory.getQuantity()> 0)
                            .build()
                ).toList();
    }


}
