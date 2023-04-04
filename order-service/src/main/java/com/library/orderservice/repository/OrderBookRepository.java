package com.library.orderservice.repository;

import com.library.orderservice.model.OrderBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBookRepository extends MongoRepository<OrderBook, String> {
}
