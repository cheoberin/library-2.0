package com.library.authservice.controller;

import com.library.authservice.model.Role;
import com.library.authservice.model.RoleToUserForm;
import com.library.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
@Slf4j
public class RoleController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getEmail(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/listRoles")
    public ResponseEntity<List<Role>>getAllRoles() {
        return ResponseEntity.ok().body(userService.getRoles());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{roleName}")
    public ResponseEntity<Role>deleteRole(@PathVariable String roleName){
        userService.deleteRole(roleName);
        return ResponseEntity.noContent().build();
    }

}
