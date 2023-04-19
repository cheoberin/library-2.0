package com.library.authservice.dto;

import com.library.authservice.model.User;


public record UserResponse(String _id, String name) {
    public UserResponse(User user){
        this(user.get_id(), user.getName());
    }

}


