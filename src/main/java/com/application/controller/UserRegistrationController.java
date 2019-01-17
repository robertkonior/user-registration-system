package com.application.controller;

import com.application.dto.UserDto;
import com.application.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserRegistrationController {

    @Autowired
    private UserServices services;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getListAllUsers() {
        return services.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") final Long id) {
        return services.getUser(id);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody final UserDto user) {
        return services.addUser(user);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") final Long id, final @RequestBody UserDto user) {
        return services.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") final Long id) {
        return services.deleteUser(id);
    }

}
