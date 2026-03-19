package com.example.AuthMicroservices.service;

import com.example.AuthMicroservices.model.AuthReq;
import com.example.AuthMicroservices.model.AuthResp;
import com.example.AuthMicroservices.proxy.AuthProxy;
import com.example.AuthMicroservices.proxy.VerifyOtpRequest;

public interface AuthService {

    String register(AuthProxy authProxy);

    AuthResp login(AuthReq authReq);

    String forgotPassword(AuthReq authReq);

    String verifyOtp(VerifyOtpRequest verifyOtpRequest);

     Boolean verifyToken(String token);

}
