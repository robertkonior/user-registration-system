package com.application.controller;

import com.application.dto.UserDto;
import com.application.exception.CustomErrorType;
import com.application.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserRegistrationController {

    private UserJpaRepository userJpaRepository;

    @Autowired
    public void setUserJpaRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> listAllUsers() {
        List<UserDto> users = userJpaRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody final UserDto user) {
        if (userJpaRepository.findByName(user.getName()) != null) {
            return new ResponseEntity<UserDto>
                    (new CustomErrorType("Unable to create new user. A User with name "
                    + user.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        userJpaRepository.save(user);
        return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") final Long id) {
        Optional<UserDto> user = userJpaRepository.findById(id);

        return user.map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(new CustomErrorType("User with id "
                        + id + " not found"), HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") final Long id, final @RequestBody UserDto user) {
        Optional<UserDto> currentUser = userJpaRepository.findById(id);

        if (currentUser.isPresent()) {
            userJpaRepository.saveAndFlush(new UserDto(id, user.getName(), user.getAddress(), user.getEmail()));
            return new ResponseEntity<UserDto>(currentUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity<UserDto>(
                new CustomErrorType("Unable to upate. User with id " + id + " not found."),
                HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") final Long id) {
        Optional<UserDto> user = userJpaRepository.findById(id);

        if (user.isPresent()) {
            userJpaRepository.deleteById(id);
            return new ResponseEntity<UserDto>(new CustomErrorType("Deleted User with id "+ id + "."),
                    HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<UserDto>
                (new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                        HttpStatus.NOT_FOUND);
    }
}
