package com.example.AuthMicroservices.controller;

import com.example.AuthMicroservices.model.AuthReq;
import com.example.AuthMicroservices.model.AuthResp;
import com.example.AuthMicroservices.proxy.AuthProxy;
import com.example.AuthMicroservices.proxy.VerifyOtpRequest;
import com.example.AuthMicroservices.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.engine.spi.Resolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/get")
    public String getURI(HttpServletRequest request){
        return request.getRequestURI().toString();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthProxy authProxy){
        return new ResponseEntity<>(authService.register(authProxy), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResp> login(@RequestBody AuthReq authReq){
        return new ResponseEntity<>(authService.login(authReq),HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgot(@RequestBody AuthReq authReq){
        return new ResponseEntity<>(authService.forgotPassword(authReq),HttpStatus.OK);
    }

    @PostMapping("/VerifyOtp")
    public ResponseEntity<String> verifyOtp( @RequestBody VerifyOtpRequest req)
    {
        return new ResponseEntity<>(authService.verifyOtp(req),HttpStatus.OK);
    }

    @PostMapping("/verifyToken")
    public ResponseEntity<Boolean>  verifyToken(@RequestBody String token){
        return new ResponseEntity<>(authService.verifyToken(token),HttpStatus.OK);
    }
}
