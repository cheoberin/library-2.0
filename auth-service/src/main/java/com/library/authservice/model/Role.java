package com.library.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;

}
