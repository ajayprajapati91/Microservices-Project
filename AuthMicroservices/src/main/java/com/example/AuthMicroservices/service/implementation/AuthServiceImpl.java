package com.example.AuthMicroservices.service.implementation;

import com.example.AuthMicroservices.entity.Auth;
import com.example.AuthMicroservices.helper.MapperHelper;
import com.example.AuthMicroservices.model.AuthReq;
import com.example.AuthMicroservices.model.AuthResp;
import com.example.AuthMicroservices.proxy.AuthProxy;
import com.example.AuthMicroservices.proxy.VerifyOtpRequest;
import com.example.AuthMicroservices.repository.AuthRepo;
import com.example.AuthMicroservices.service.AuthService;
import com.example.AuthMicroservices.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MapperHelper mapperHelper;

    @Autowired
    private AuthRepo authRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    private String OTP;

    @Override
    public String register(AuthProxy authProxy) {
        authProxy.setPassword(passwordEncoder.encode(authProxy.getPassword()));
        Auth auth = mapperHelper.proxyToEntityAuth(authProxy);
        authRepo.save(auth);
        return "User registered successfully";
    }

    @Override
    public AuthResp login(AuthReq authReq) {


        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
        if (authenticate.isAuthenticated()) {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(authReq.getUsername());

            Date expiredOn = new Date();
            expiredOn.setTime(expiredOn.getTime() + 1000 * 60 * 60 * 5);
            System.out.println("Expiry DateTime ->" + expiredOn);
            String jwtToken = jwtUtil.generateToken(userDetails, expiredOn);

            return AuthResp.builder()
                    .token(jwtToken)
                    .username(authReq.getUsername())
                    .role(userDetails.getAuthorities().toString())
                    .expiredOn(expiredOn)
                    .build();
        }
        return null;
    }

    @Override
    public String forgotPassword(AuthReq authReq) {
        Auth usernameFound = authRepo.findByUsername(authReq.getUsername()).orElseThrow
                (() -> new UsernameNotFoundException("Username not found"));
//        String string = UUID.randomUUID().toString().substring(0,8);
//        usernameFound.setPassword(encoder.encode(l.getPassword()));
//       usernameFound.setPassword(encoder.encode(string));
//        userRepo.save(usernameFound);
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        String string = String.valueOf(otp);
        OTP=string;

        return string;
    }

    @Override
    public String verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        if(verifyOtpRequest.getUsername()== null || verifyOtpRequest.getOtp()==null){
            return "Enter valid otp and username";
        }
        Auth auth = authRepo.findByUsername(verifyOtpRequest.getUsername()).get();
        if (OTP.equals(verifyOtpRequest.getOtp())){
            auth.setPassword(passwordEncoder.encode(verifyOtpRequest.getPassword()));
            authRepo.save(auth);
            return "Password updated successfully";
        }
        return "Password updation fail . Enter valid otp";
    }

    @Override
    public Boolean verifyToken(String token) {
        try{
            String username = jwtUtil.extractUsername(token);
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            return jwtUtil.validateToken(token,userDetails);
        }catch (Exception e){
            return false;
        }
    }
}
