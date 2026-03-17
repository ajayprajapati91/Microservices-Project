package com.example.AuthMicroservices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResp {

    private String username;
    private String token;
    private String role;
    private Date expiredOn;
}
