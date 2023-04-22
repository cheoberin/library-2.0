package com.library.orderservice.dto;

import com.library.orderservice.model.OrderItems;

import java.math.BigDecimal;

public record OrderItemsDetails(String _id, String bookId, String bookName, BigDecimal price, int quantity) {

    public OrderItemsDetails(OrderItems orderItems) {
        this(orderItems.get_id(), orderItems.getBookId(), orderItems.getBookName(), orderItems.getPrice(), orderItems.getQuantity());
    }

}
