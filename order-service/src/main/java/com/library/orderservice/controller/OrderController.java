package com.library.orderservice.controller;

import com.library.orderservice.dto.OrderDetails;
import com.library.orderservice.dto.OrderRequest;
import com.library.orderservice.dto.OrderResponse;
import com.library.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDetails> placeOrder(@RequestBody OrderRequest orderRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(orderRequest));
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderDetails> getOrder(@PathVariable String orderNumber){
        return ResponseEntity.ok().body(orderService.getOrder(orderNumber));
    }
    @GetMapping("/")
    public ResponseEntity<List<OrderDetails>> getOrderByUserId(@RequestParam String userId){
        return ResponseEntity.ok().body(orderService.getOrderbyUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll(){
        return ResponseEntity.ok().body(orderService.findall());
    }



}
