package com.example.inventoryservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
public class Inventory {
    @Id
    private String _id;
    private String bookId;
    private int quantity;
}
