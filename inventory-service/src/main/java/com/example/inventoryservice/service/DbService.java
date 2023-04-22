package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DbService {

    private final InventoryRespository inventoryRespository;

    public void InitDb(){
        Inventory inventory1 = new Inventory();
        inventory1.setBookId("64399067f380c202e5ea4b96");
        inventory1.setQuantity(1000);
        Inventory inventory2 = new Inventory();
        inventory2.setBookId("643c1693c960b86c3cdab29f");
        inventory2.setQuantity(200);
    }
}
