package com.library.orderservice.model;

import com.library.orderservice.dto.OrderRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
public class Order {

    @Id
    private String _id;
    @NotBlank
    private String customerId;
    @NotBlank
    private String customerName;
    @NotBlank
    private String phone;
    @NotNull
    private Address address;
    @NotNull
    private List<OrderBook> orderBookList;

    public Order(OrderRequest orderRequest) {
        this.customerId = orderRequest.customerId();
        this.customerName = orderRequest.customerName();
        this.phone = orderRequest.phone();
        this.address = orderRequest.address();
        this.orderBookList = orderRequest.orderBookList();
    }
}
