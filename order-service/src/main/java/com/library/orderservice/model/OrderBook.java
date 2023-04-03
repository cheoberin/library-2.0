package com.library.orderservice.model;

import com.library.orderservice.dto.OrderBookRequest;
import com.library.orderservice.dto.OrderBookUpdate;
import jakarta.validation.Valid;
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

    public OrderBook(@Valid OrderBookRequest orderBookRequest) {
        this.bookId = orderBookRequest.bookName();
        this.bookName = orderBookRequest.bookName();
        this.price = orderBookRequest.price();
        this.quantity = orderBookRequest.quantity();
    }

    public void update(@Valid OrderBookUpdate orderBookUpdate) {
        if (orderBookUpdate.bookId() != null) {
            this.bookId = orderBookUpdate.bookName();
        }

        if (orderBookUpdate.bookName() != null) {
            this.bookName = orderBookUpdate.bookName();
        }

        if (orderBookUpdate.price() != null) {
            this.price = orderBookUpdate.price();
        }

        if (orderBookUpdate.quantity() != 0) {
            this.quantity = orderBookUpdate.quantity();
        }
    }


}
