package com.application.repository;

import com.application.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserDto, Long> {
    UserDto findByName(String name);
}
