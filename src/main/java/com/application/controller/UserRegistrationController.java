package com.application.controller;

import com.application.dto.UserDto;
import com.application.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserRegistrationController {

    public static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);
    private UserJpaRepository userJpaRepository;

    @Autowired
    public void setUserJpaRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> listAllUsers() {
        List<UserDto> users = userJpaRepository.findAll();
        return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody final UserDto user) {
        userJpaRepository.save(user);
        return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
    }

}
