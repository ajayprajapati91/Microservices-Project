package com.intern.authservice.service;

import com.intern.authservice.domain.Users;
import com.intern.authservice.proxy.*;

import java.util.List;

public interface AuthService {
    String register(UserProxy proxy);
    AuthResponse login(AuthRequest request);
    String forgetPassword(ForgetPassword forgetPassword);
    Boolean validateToken(TokenRoleProxy tokenRoleProxy);
    Users getUser(Long id);
    List<Users> getAllUser();
}
