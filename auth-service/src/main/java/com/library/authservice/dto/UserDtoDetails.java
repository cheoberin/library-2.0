package com.library.authservice.dto;


import com.library.authservice.model.Role;
import com.library.authservice.model.User;

import java.util.List;

public record UserDtoDetails(String _id, String name, String cpf, String phone, String email, List<Role> roles) {
    public UserDtoDetails(User user){
        this(user.get_id(), user.getName(), user.getCpf(), user.getPhone(),user.getEmail(),user.getRoles());
    }
}
