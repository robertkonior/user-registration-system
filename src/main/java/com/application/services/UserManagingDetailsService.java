package com.application.services;

import com.application.dto.UserManagingSysDto;
import com.application.repository.UserManagingJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
public class UserManagingDetailsService implements UserDetailsService {

    @Autowired
    private UserManagingJpaRepository userManagingJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserManagingSysDto user = userManagingJpaRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Oops! user not found with user-name: " + username);
        }
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Collection<GrantedAuthority> getAuthorities(UserManagingSysDto user) {
        return createAuthorityList(user.getRole());
    }
}
