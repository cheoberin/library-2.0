package com.library.orderservice.dto;

import com.library.orderservice.model.Address;
import com.library.orderservice.model.Order;
import com.library.orderservice.model.OrderBook;

import java.util.List;

public record OrderDetails(
        String _id,
        String customerId,
        String customerName,
        String phone,
        Address address,
        List<OrderBook> orderBookList
) {

    public OrderDetails(Order order) {
        this(order.get_id(), order.getCustomerId(), order.getCustomerName(), order.getPhone(), order.getAddress(), order.getOrderBookList());
    }

}
