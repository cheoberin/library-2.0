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
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemsService orderItemsService;
    private final WebClient webClient;
    public OrderDetails placeOrder(OrderRequest orderRequest){

        Order order = new Order(orderRequest);
        Address adress = addressRepository.findById(orderRequest.addressId())
                .orElseThrow(() -> new NotFoundException("Address: " + orderRequest.addressId() + " Not found"));

        List<OrderItems> orderItemsList = orderItemsService.save(orderRequest.orderItemsList());

        order.setOrderItemsList(orderItemsList);
        order.setAddress(adress);
        order.setOrderNumber(UUID.randomUUID().toString());

        List<String> bookIds = order.getOrderItemsList()
                .stream()
                .map(OrderItems::getBookId)
                .toList();
        boolean allProductsinStock;

        try {
            InventoryResponse[] inventoryResponsesArray = webClient.get()
                    .uri("http://localhost:8090/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("bookIds", bookIds).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            allProductsinStock = Arrays.stream(Objects.requireNonNull(inventoryResponsesArray)).allMatch(InventoryResponse::isInStock);

        }catch (Exception e){
            throw new NotFoundException(e.getMessage());
        }



        if(allProductsinStock) {
            return new OrderDetails(orderRepository.save(order));
        }else {
            throw new NotFoundException("product is not in stock");
        }

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

}
