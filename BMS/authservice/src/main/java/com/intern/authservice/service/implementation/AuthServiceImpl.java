package com.intern.authservice.service.implementation;

import com.intern.authservice.domain.Users;
import com.intern.authservice.proxy.*;
import com.intern.authservice.repository.UserRepo;
import com.intern.authservice.service.AuthService;
import com.intern.authservice.utility.JwtUtil;
import com.intern.authservice.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MapperHelper mapperHelper;

    @Autowired
    private MyUserDetailsService myUsersDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String register(UserProxy proxy) {
        if(userRepo.findByEmail(proxy.getEmail()).isPresent()){
            throw new RuntimeException(proxy.getEmail() +" email id is already present");
        }
        Users users = mapperHelper.map(proxy, Users.class);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        userRepo.save(users);
        return "Register successfully";
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        AuthResponse authResponse = new AuthResponse();

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if(authenticate.isAuthenticated()){
            UserDetails userDetails = myUsersDetailsService.loadUserByUsername(request.getEmail());
            String jwtToken = jwtUtil.generateToken(userDetails);
            authResponse.setEmail(request.getEmail());
            authResponse.setToken(jwtToken);
        }

        return authResponse;
    }

    @Override
    public String forgetPassword(ForgetPassword forgetPassword) {
        String email = forgetPassword.getEmail();
        Optional<Users> optUser = userRepo.findByEmail(email);
        if(optUser.isPresent()){
            Users users = optUser.get();
            users.setPassword(passwordEncoder.encode(forgetPassword.getPassword()));
            return userRepo.save(users).toString();
        }
        throw new UsernameNotFoundException("user not found with email "+email);
    }

    @Override
    public Boolean validateToken(TokenRoleProxy tokenRoleProxy) {
        String authToken = tokenRoleProxy.getToken();
        String role = tokenRoleProxy.getRole();
        try{
            if (authToken != null && authToken.startsWith("Bearer ")) {
                String jwtToken = authToken.substring(7);
                String username = jwtUtil.extractUsername(jwtToken);

                if (username != null) {
                    UserDetails userDetails = myUsersDetailsService.loadUserByUsername(username);

                    List<String> roles = jwtUtil.extractClaim(jwtToken, claims -> claims.get("roles", ArrayList.class));
                    List<SimpleGrantedAuthority> list = roles.stream().map(SimpleGrantedAuthority::new).toList();

                    if (jwtUtil.validateToken(jwtToken, userDetails)) {
                        System.out.println("isValidatedToken");
                        if(roles.contains(role)){
                            return true;
                        }
                        else{
                            throw new RuntimeException("Not Valid Role");
                        }
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public Users getUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("No user found with id "+id));
    }

    @Override
    public List<Users> getAllUser() {
        System.out.println("Service "+userRepo.findAll());
        return userRepo.findAll();
    }
}
