package com.library.orderservice.dto;

import com.library.orderservice.model.OrderBook;

import java.math.BigDecimal;

public record OrderBookResponse(String _id, String bookId, String bookName, BigDecimal price, int quantity) {

    public OrderBookResponse(OrderBook orderBook) {
        this(orderBook.get_id(), orderBook.getBookId(), orderBook.getBookName(), orderBook.getPrice(), orderBook.getQuantity());
    }

}
