package com.example.AuthMicroservices.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long usersId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;
    private String phoneNo;

}
