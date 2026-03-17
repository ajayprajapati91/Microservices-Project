//package com.example.AuthMicroservices.filter;
//
//import com.example.AuthMicroservices.service.implementation.MyUserDetailsService;
//import com.example.AuthMicroservices.utils.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private MyUserDetailsService myUserDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String authToken = request.getHeader("Authorization");
//        String token = null;
//        String  username = null;
//
//        if(authToken!=null && authToken.startsWith("Bearer ")){
//            token = authToken.substring(7);
//            username = jwtUtil.extractUsername(token);
//
//            if(jwtUtil.isBlacklisted(token)){
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//
//            if(username!=null && token != null) {
//                UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
//
//
//                List<String> roleString = jwtUtil.extractClaim(token, claims -> claims.get("roles", ArrayList.class));
//                List<SimpleGrantedAuthority> SimpleGrantedAuthlist = new ArrayList<>();
//
//                for (String role : roleString) {
//                    SimpleGrantedAuthority s = new SimpleGrantedAuthority(role);
//                    SimpleGrantedAuthlist.add(s);
//                }
//
//                if (jwtUtil.validateToken(token, userDetails) && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, SimpleGrantedAuthlist);
//                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(auth);
//
//                }
//            }
//        }
//
//        filterChain.doFilter(request,response);
//
//    }
//}
