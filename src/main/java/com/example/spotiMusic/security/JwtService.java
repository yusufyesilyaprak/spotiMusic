package com.example.spotiMusic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Secret key for encryption (Normally stored in application.properties or environment variables)
    // Must be at least 256-bit long and Base64 encoded.
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    // 1. Generate Token
    public String generateToken(String userEmail) {
        return generateToken(new HashMap<>(), userEmail);
    }

    public String generateToken(Map<String, Object> extraClaims, String userEmail) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours validity
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. Extract Email from Token
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 3. Token Validity Check
    public boolean isTokenValid(String token, String userEmail) {
        final String email = extractUserEmail(token);
        return (email.equals(userEmail)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}