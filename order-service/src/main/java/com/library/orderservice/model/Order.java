package com.library.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Order {

    @Id
    private String _id;
    private String name;

}
