package com.example.AuthMicroservices.service.implementation;

import com.example.AuthMicroservices.entity.Auth;
import com.example.AuthMicroservices.model.AuthReq;
import com.example.AuthMicroservices.repository.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthRepo authRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Auth> byUsername =  authRepo.findByUsername(username);
        if(byUsername.isPresent()){
            Auth auth = byUsername.get();

            String roleString = auth.getRole();
            String[] split = roleString.split(",");

            List<SimpleGrantedAuthority> list = Arrays.stream(split).map(r -> new SimpleGrantedAuthority(r)).toList();

            return User.builder()
                    .username(auth.getUsername())
                    .password(auth.getPassword())
                    .authorities(list)
                    .build();

        }
        throw new UsernameNotFoundException("Student not found"+username);

    }
}
