package com.library.authservice.controller;

import com.library.authservice.dto.UserDtoDetails;
import com.library.authservice.dto.UserResponse;
import com.library.authservice.model.User;
import com.library.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN', 'EMPLOYEE')")
    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN', 'EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDtoDetails> findById(@PathVariable String id) {
        UserDtoDetails user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN', 'EMPLOYEE')")
    @GetMapping("/getUser/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User newUser) {
        User user = userService.updateUser(newUser);
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }


}