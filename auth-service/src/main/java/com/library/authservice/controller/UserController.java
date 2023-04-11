package com.library.authservice.controller;

import com.library.authservice.model.Role;
import com.library.authservice.model.RoleToUserForm;
import com.library.authservice.model.User;
import com.library.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/list")
    public ResponseEntity<List<User>>getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        User user =  userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/save")
    public ResponseEntity<User>saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @GetMapping("/getUser/{username}")
    public ResponseEntity<User>getUser(@PathVariable String username) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.getUser(username));
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User newUser) {
        User user = userService.updateUser(newUser);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<User>deleteUser(@PathVariable String username){
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }


    @PostMapping("/role/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/role/listRoles")
    public ResponseEntity<List<Role>>getAllRoles() {
        return ResponseEntity.ok().body(userService.getRoles());
    }

    @DeleteMapping("/role/delete/{roleName}")
    public ResponseEntity<Role>deleteRole(@PathVariable String roleName){
        userService.deleteRole(roleName);
        return ResponseEntity.noContent().build();
    }


}