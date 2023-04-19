package com.library.authservice.model;

import lombok.Data;

@Data
public class RoleToUserForm {
    private String email;
    private String roleName;
}