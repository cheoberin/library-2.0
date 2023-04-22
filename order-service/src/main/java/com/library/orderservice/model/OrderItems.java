package com.library.orderservice.model;

import com.library.orderservice.dto.OrderItemsDto;
import com.library.orderservice.dto.OrderItemsUpdate;
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
public class OrderItems {

    @Id
    private String _id;
    private String bookId;
    private String bookName;
    private BigDecimal price;
    private int quantity;

    public OrderItems(@Valid OrderItemsDto orderItemsDto) {
        this.bookId = orderItemsDto.bookId();
        this.bookName = orderItemsDto.bookName();
        this.price = orderItemsDto.price();
        this.quantity = orderItemsDto.quantity();
    }

    public void update(@Valid OrderItemsUpdate orderItemsUpdate) {
        if (orderItemsUpdate.bookId() != null) {
            this.bookId = orderItemsUpdate.bookId();
        }

        if (orderItemsUpdate.bookName() != null) {
            this.bookName = orderItemsUpdate.bookName();
        }

        if (orderItemsUpdate.price() != null) {
            this.price = orderItemsUpdate.price();
        }

        if (orderItemsUpdate.quantity() != 0) {
            this.quantity = orderItemsUpdate.quantity();
        }
    }


}
