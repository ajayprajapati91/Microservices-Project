package com.intern.authservice.service.implementation;

import com.intern.authservice.domain.Users;
import com.intern.authservice.repository.UserRepo;
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
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optUser = userRepo.findByEmail(username);

        if(optUser.isPresent()){
            Users users = optUser.get();
            String role = users.getRole();
            String[] split = role.split(",");
            List<SimpleGrantedAuthority> authorities = Arrays.stream(split).map(SimpleGrantedAuthority::new).toList();

            return User.builder()
                    .username(users.getEmail())
                    .password(users.getPassword())
                    .authorities(authorities)
                    .build();
        }

        throw new UsernameNotFoundException("There is No user found with email id "+username);
    }
}