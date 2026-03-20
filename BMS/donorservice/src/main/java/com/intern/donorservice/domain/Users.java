package com.intern.donorservice.domain;

import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
public class Users {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String phone;
    private String status;
}
