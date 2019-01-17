package com.application.repository;

import com.application.dto.UserManagingSysDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserManagingJpaRepository extends JpaRepository<UserManagingSysDto , Long> {
    UserManagingSysDto findByUsername(String username);
}
