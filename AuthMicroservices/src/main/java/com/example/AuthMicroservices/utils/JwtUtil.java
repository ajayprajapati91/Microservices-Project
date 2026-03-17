package com.example.AuthMicroservices.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String SECRET_KEY="VQ8PqaN_tJUZq1c6Z0N5P2O-odPB4e7-zgEkyUZYYWc";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails, Date expiredOn) {
        Map<String, Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        List<String> roles = new ArrayList<>();

        for(GrantedAuthority authority : authorities){
            roles.add(authority.getAuthority());
        }
        claims.put("roles",roles);
        return createToken(claims, userDetails.getUsername(),expiredOn);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey signingKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject, Date expiredOn) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .header()
                .type("jwt")
                .add("id", "ajay")
                .and()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(now))
                .expiration(expiredOn)
                .signWith(signingKey(),Jwts.SIG.HS256)
                .compact();
    }

    public String getSecretKey() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        String base64UrlKey = Encoders.BASE64URL.encode(key.getEncoded());
        return base64UrlKey;
    }

    private Set<String> blacklist = ConcurrentHashMap.newKeySet();

    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
