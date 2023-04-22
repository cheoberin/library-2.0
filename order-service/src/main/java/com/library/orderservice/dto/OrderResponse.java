package com.library.orderservice.dto;

import com.library.orderservice.model.Address;
import com.library.orderservice.model.Order;

import java.util.List;

public record OrderResponse(String orderNumber,String customerName, String status, Address address, List<OrderItemsResponse> orderItemsList) {
    public OrderResponse(Order order){
        this(order.getOrderNumber(), order.getCustomerName(), order.getStatus(), order.getAddress(), order.getOrderItemsList().stream().map(OrderItemsResponse::new).toList());
    }
}

