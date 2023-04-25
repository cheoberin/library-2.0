package com.library.authservice.service;

import com.library.authservice.dto.UserDtoDetails;
import com.library.authservice.dto.UserResponse;
import com.library.authservice.model.Role;
import com.library.authservice.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    User updateUser(User user);
    void addRoleToUser(String username, String roleName);
    UserResponse getUser(String username);
    UserDtoDetails findById(String id);
    List<UserResponse> getUsers();
    List<Role> getRoles();
    void deleteUser(String username);
    void deleteRole (String roleName);
}
