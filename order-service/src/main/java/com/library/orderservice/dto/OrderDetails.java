package com.library.orderservice.dto;

import com.library.orderservice.model.Address;
import com.library.orderservice.model.Order;
import com.library.orderservice.model.OrderItems;

import java.util.List;

public record OrderDetails(
        String _id,
        String customerId,
        String customerName,
        String orderNumber,
        String phone,
        Address address,
        List<OrderItems> orderItemsList
) {

    public OrderDetails(Order order) {
        this(order.get_id(), order.getCustomerId(), order.getCustomerName(), order.getOrderNumber(), order.getPhone(), order.getAddress(), order.getOrderItemsList());
    }

}
