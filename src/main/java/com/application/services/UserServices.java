package com.application.services;

import com.application.dto.UserDto;
import com.application.exception.CustomErrorType;
import com.application.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public static final Logger logger = LoggerFactory.getLogger(UserServices.class);

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
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity<UserDto>
                    (new CustomErrorType("Unable to create new user. A User with name "
                            + user.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        repository.save(user);
        logger.info("Creating User : {}", user);
        return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
    }

    public ResponseEntity<UserDto> updateUser(final Long id, final UserDto user) {
        Optional<UserDto> currentUser = repository.findById(id);

        if (currentUser.isPresent()) {
            repository.saveAndFlush(new UserDto(id, user.getName(), user.getAddress(), user.getEmail()));
            logger.info("Updating user with id : {}", id);
            return new ResponseEntity<UserDto>(currentUser.get(), HttpStatus.OK);
        }
        logger.error("Unable to upate. User with id " + id + " not found.");
        return new ResponseEntity<UserDto>(
                new CustomErrorType("Unable to update. User with id " + id + " not found."),
                HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<UserDto> deleteUser(final Long id) {
        Optional<UserDto> user = repository.findById(id);

        if (user.isPresent()) {
            repository.deleteById(id);
            logger.info("Deleted User with id " + id + ".");
            return new ResponseEntity<UserDto>(new CustomErrorType("Deleted User with id " + id + "."),
                    HttpStatus.NO_CONTENT);
        }
        logger.error("Unable to delete. User with id " + id + " not found.");
        return new ResponseEntity<UserDto>
                (new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                        HttpStatus.NOT_FOUND);
    }
}
