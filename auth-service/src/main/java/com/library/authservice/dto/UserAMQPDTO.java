package com.library.authservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.library.authservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonSerialize
@AllArgsConstructor
@Data
public class UserAMQPDTO {
    private String username;
    private String name;
    private String email;

    public UserAMQPDTO(User user) {
        this.username = user.getEmail();
        this.name = user.getName();
        this.email = user.getEmail();
    }

}
