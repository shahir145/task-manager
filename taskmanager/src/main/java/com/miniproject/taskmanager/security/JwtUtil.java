package com.miniproject.taskmanager.security;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    //creates a token that proves who the user is.
    //user logs in with username and password →
    //you verify their credentials →
    //you generate a token that says "this is [username],
    //I verified them at [time], this is valid until [expiry]"
    //→ the user sends that token with every future request so they don't have to log in every time.
    public String generateToken(String identifier) {
        return Jwts.builder()
                .subject(identifier)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    //the username is stored inside the token as the "subject".
    //this is reading it back out.
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //when you generate the token you sign it with your secret key.
    // That signature is mathematically tied to the token's content.
    // If anyone tampers with the token the signature no longer matches.
    // When you validate it, you're checking:
    //Is the signature valid? (wasn't tampered with)
    //Has it expired?
    //The signing key is the secret that only your server knows.
    // It's what makes the whole thing trustworthy —
    // anyone can read a JWT but only your server can verify it's genuine.
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}