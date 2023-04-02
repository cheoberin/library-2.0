package com.library.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "order_book")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderBook {

    @Id
    private String _id;
    private String bookId;
    private String bookName;
    private BigDecimal price;
    private int quantity;

}
