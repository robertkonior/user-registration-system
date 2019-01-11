package com.application.dto;

import org.hibernate.validator.constraints.Length;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "Users")
public class UserDto {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @NotEmpty(message = "error.name.empty")
    @Length(max = 50,  message = "error.name.length")
    @Column(name = "NAME")
    private String name;

    @NotEmpty(message = "error.address.empty")
    @Length(max = 1500, message = "error.address.length")
    @Column(name = "ADDRESS")
    private String address;

    @Email(message = "error.email.email")
    @NotEmpty(message = "error.email.empty")
    @Length(max = 80,  message = "error.email.length")
    @Column(name = "EMAIL")
    private String email;

    public UserDto() {
    }

    public UserDto(Long id, String name, String address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
