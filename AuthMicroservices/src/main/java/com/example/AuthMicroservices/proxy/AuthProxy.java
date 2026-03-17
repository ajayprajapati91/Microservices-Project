package com.example.AuthMicroservices.proxy;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthProxy {

    private Long usersId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;
    private String phoneNo;
}
