package com.library.orderservice.service;

import com.library.orderservice.dto.InventoryResponse;
import com.library.orderservice.dto.OrderDetails;
import com.library.orderservice.dto.OrderRequest;
import com.library.orderservice.dto.OrderResponse;
import com.library.orderservice.exception.NotFoundException;
import com.library.orderservice.model.Address;
import com.library.orderservice.model.Order;
import com.library.orderservice.model.OrderItems;
import com.library.orderservice.repository.AddressRepository;
import com.library.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemsService orderItemsService;
    private final WebClient.Builder webClientbuilder;

    public OrderDetails placeOrder(OrderRequest orderRequest){
        log.info("order, {}", orderRequest);
        Order order = new Order(orderRequest);
        Address adress = addressRepository.findById(orderRequest.addressId())
                .orElseThrow(() -> new NotFoundException("Address: " + orderRequest.addressId() + " Not found"));

        List<OrderItems> orderItemsList = orderItemsService.save(orderRequest.orderItemsList());

        order.setOrderItemsList(orderItemsList);
        order.setAddress(adress);
        order.setOrderNumber(UUID.randomUUID().toString());

        /*List<String> bookIds = order.getOrderItemsList()
                .stream()
                .map(OrderItems::getBookId)
                .toList();




            String bookIdsParam = String.join(",", bookIds);
            InventoryResponse[] inventoryResponsesArray = webClientbuilder.build().get()
                    .uri("http://inventory-service/api/inventory/?bookIds={bookIds}",
                            bookIdsParam)
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            boolean allProductsinStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);
            System.out.println(Arrays.toString(inventoryResponsesArray));
            log.info("deu certo 1:{}",allProductsinStock);
        */

        //if(allProductsinStock) {
            return new OrderDetails(orderRepository.save(order));
        //}else {
         //   throw new NotFoundException("product is not in stock");
        //}

    }
    public OrderDetails getOrder(String ordernumber){
      return new OrderDetails(orderRepository.findByOrderNumber(ordernumber)
              .orElseThrow(
                      () -> new NotFoundException("Order: " + ordernumber + " Not found")));
    }
    public List<OrderResponse> findall(){
       List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderResponse::new).toList();
    }

    public List<OrderDetails> getOrderbyUserId(String userId){
        List<Order> orders = orderRepository.findOrdersByCustomerId(userId).orElseThrow(
                () -> new NotFoundException("Order: " + userId + " Not found"));
        return orders.stream().map(OrderDetails::new).toList();

    }
}
