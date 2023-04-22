package com.library.orderservice.repository;

import com.library.orderservice.model.OrderItems;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository  extends MongoRepository<OrderItems,String> {

}
