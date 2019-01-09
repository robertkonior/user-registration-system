package com.application.services;

import com.application.dto.UserDto;
import com.application.exception.CustomErrorType;
import com.application.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    UserJpaRepository repository;

    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = repository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
    }

    public ResponseEntity<UserDto> getUser(final Long id) {
        Optional<UserDto> user = repository.findById(id);

        return user.map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(new CustomErrorType("User with id "
                        + id + " not found"), HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<UserDto> addUser(final UserDto user) {
        if (repository.findByName(user.getName()) != null) {
            return new ResponseEntity<UserDto>
                    (new CustomErrorType("Unable to create new user. A User with name "
                            + user.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        repository.save(user);
        return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
    }

    public ResponseEntity<UserDto> updateUser(final Long id, final UserDto user) {
        Optional<UserDto> currentUser = repository.findById(id);

        if (currentUser.isPresent()) {
            repository.saveAndFlush(new UserDto(id, user.getName(), user.getAddress(), user.getEmail()));
            return new ResponseEntity<UserDto>(currentUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity<UserDto>(
                new CustomErrorType("Unable to upate. User with id " + id + " not found."),
                HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<UserDto> deleteUser(final Long id) {
        Optional<UserDto> user = repository.findById(id);

        if (user.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<UserDto>(new CustomErrorType("Deleted User with id " + id + "."),
                    HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<UserDto>
                (new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                        HttpStatus.NOT_FOUND);
    }
}
