package com.job.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // GET SIGNING KEY
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // GENERATE TOKEN
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                        System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(),
                        SignatureAlgorithm.HS256) // ← FIXED!
                .compact();
    }

    // EXTRACT USERNAME
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // VALIDATE TOKEN
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username)
                && !isTokenExpired(token));
    }

    // IS TOKEN EXPIRED
    private boolean isTokenExpired(String token) {
        Date expiry = getClaims(token).getExpiration();
        return expiry.before(new Date());
    }

    // GET CLAIMS → PRIVATE HELPER METHOD
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()  // ← FIXED! parserBuilder not parser
                .setSigningKey(getSigningKey()) // ← FIXED! Key not String
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}