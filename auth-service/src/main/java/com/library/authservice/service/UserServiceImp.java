package com.library.authservice.service;

import com.library.authservice.exceptions.ObjectNotFoundException;
import com.library.authservice.model.Role;
import com.library.authservice.model.User;
import com.library.authservice.repository.RoleRepository;
import com.library.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database...", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));



        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database...", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public User updateUser(User user) {
        User userOriginal = this.findById(user.get_id());
        userOriginal.setPassword(user.getPassword());
        userOriginal.setName(user.getName());
        userOriginal.setRoles(user.getRoles());
        userOriginal.setEmail(user.getEmail());
        return userRepository.save(userOriginal);
    }


    @Override
    public void addRoleToUser(String username, String roleName) {

        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));

        Role role = roleRepository.findByName(roleName);

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
       Optional<User> user = userRepository.findByEmail(username);

       return user.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
    }


    @Override
    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Object not Found: " + id + " , type: " +
                User.class.getName()));
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteUser(String username) {
        User user = this.getUser(username);
        userRepository.delete(user);
        log.info("Role {} deleted", username);
    }

    @Override
    public void deleteRole(String roleName) {
        Role role = roleRepository.findByName(roleName);
        roleRepository.delete(role);
        log.info("Role {} deleted", roleName);
    }

}
