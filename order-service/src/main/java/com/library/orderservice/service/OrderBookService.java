package com.library.orderservice.service;

import com.library.orderservice.dto.OrderBookDetails;
import com.library.orderservice.dto.OrderBookRequest;
import com.library.orderservice.dto.OrderBookResponse;
import com.library.orderservice.dto.OrderBookUpdate;
import com.library.orderservice.exception.NotFoundException;
import com.library.orderservice.model.OrderBook;
import com.library.orderservice.repository.OrderBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderBookService {

    private final OrderBookRepository orderBookRepository;

    public OrderBookDetails save(OrderBookRequest orderBookRequest) {
        OrderBook orderBook = new OrderBook(orderBookRequest);
        OrderBookDetails orderBookDetails = new OrderBookDetails(orderBookRepository.save(orderBook));
        log.info("OrderBook saved: {} - {} - {}", orderBookDetails.bookName(), orderBookDetails.quantity(), orderBookDetails.price());
        return orderBookDetails;
    }

    public List<OrderBookResponse> findAll() {
        List<OrderBook> orderBooks = orderBookRepository.findAll();
        log.info("OrderBook list returned");
        return orderBooks.stream().map(OrderBookResponse::new).toList();
    }

    public void delete(String id) {
        findById(id);
        orderBookRepository.deleteById(id);
        log.warn("OrderBook deleted, id: " + id);
    }

    public OrderBookDetails findById(String id) {
        OrderBook orderBook = orderBookRepository.findById(id).orElseThrow(() -> new NotFoundException("Object not found: " + id + ", type: " + OrderBook.class.getName()));
        log.info("OrderBook returned: {} - {} - {}", orderBook.getBookName(), orderBook.getPrice(), orderBook.getQuantity());
        return new OrderBookDetails(orderBook);
    }

    public OrderBookDetails update(OrderBookUpdate orderBookUpdate) {
        OrderBook orderBook = orderBookRepository.findById(orderBookUpdate._id()).orElseThrow(() -> new NotFoundException("Object not found: " + orderBookUpdate._id() + ", type: " + OrderBook.class.getName()));
        orderBook.update(orderBookUpdate);
        orderBookRepository.save(orderBook);
        log.info("OrderBook updated, id: " + orderBook.get_id());
        return new OrderBookDetails(orderBook);
    }

}
