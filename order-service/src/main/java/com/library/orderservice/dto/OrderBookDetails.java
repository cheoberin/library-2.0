package com.library.orderservice.dto;

import com.library.orderservice.model.OrderBook;

import java.math.BigDecimal;

public record OrderBookDetails(String _id, String bookId, String bookName, BigDecimal price, int quantity) {

    public OrderBookDetails(OrderBook orderBook) {
        this(orderBook.get_id(), orderBook.getBookId(), orderBook.getBookName(), orderBook.getPrice(), orderBook.getQuantity());
    }

}
