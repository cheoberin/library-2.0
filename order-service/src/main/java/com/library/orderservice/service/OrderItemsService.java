package com.library.orderservice.service;

import com.library.orderservice.dto.OrderItemsDto;
import com.library.orderservice.model.OrderItems;
import com.library.orderservice.repository.OrderItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemsService {
    private final OrderItemsRepository orderItemsRepository;

    public List<OrderItems> save(List<OrderItemsDto> orderItemsListDto) {
        return orderItemsListDto.stream()
                .map(OrderItems::new)
                .map(orderItemsRepository::save)
                .collect(Collectors.toList());
    }
}
