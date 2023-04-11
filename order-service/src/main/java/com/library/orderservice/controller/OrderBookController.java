package com.library.orderservice.controller;

import com.library.orderservice.dto.OrderBookDetails;
import com.library.orderservice.dto.OrderBookRequest;
import com.library.orderservice.dto.OrderBookResponse;
import com.library.orderservice.dto.OrderBookUpdate;
import com.library.orderservice.service.OrderBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orderbook")
public class OrderBookController {

    private final OrderBookService orderBookService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid OrderBookRequest orderBookRequest, UriComponentsBuilder uriComponentsBuilder) {
        OrderBookDetails orderBookDetails = orderBookService.save(orderBookRequest);
        URI uri = uriComponentsBuilder.path("/api/orderbook/{id}").buildAndExpand(orderBookDetails._id()).toUri();
        return ResponseEntity.created(uri).body(orderBookDetails);
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<OrderBookResponse> orderBookResponses = orderBookService.findAll();
        return ResponseEntity.ok().body(orderBookResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        orderBookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        OrderBookDetails orderBookDetails = orderBookService.findById(id);
        return ResponseEntity.ok().body(orderBookDetails);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid OrderBookUpdate orderBookUpdate) {
        OrderBookDetails orderBookDetails = orderBookService.update(orderBookUpdate);
        return ResponseEntity.ok().body(orderBookDetails);
    }

}
