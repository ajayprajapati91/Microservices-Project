package com.intern.authservice.proxy;

import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private String email;
}
