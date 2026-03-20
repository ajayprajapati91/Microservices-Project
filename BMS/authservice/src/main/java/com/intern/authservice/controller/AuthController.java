package com.intern.authservice.controller;

import com.intern.authservice.domain.Users;
import com.intern.authservice.proxy.*;
import com.intern.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserProxy userProxy){
        return new ResponseEntity<>(authService.register(userProxy) , HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request){
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

    @PostMapping(value = "/forget")
    public ResponseEntity<String> login(@Valid @RequestBody ForgetPassword forgetPassword){
        return new ResponseEntity<>(authService.forgetPassword(forgetPassword) , HttpStatus.OK);
    }

    @PostMapping(value = "/validate")
    public ResponseEntity<Boolean> validate(@RequestBody TokenRoleProxy tokenRoleProxy){
        return new ResponseEntity<>(authService.validateToken(tokenRoleProxy) , HttpStatus.OK);
    }

    @GetMapping(value = "user/{id}")
    public Users getUser(@PathVariable Long id){
        return authService.getUser(id);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Users>> getAllUser(){
        System.out.println("Controller "+authService.getAllUser());
        return new ResponseEntity<>(authService.getAllUser(),HttpStatus.OK);
    }
}
