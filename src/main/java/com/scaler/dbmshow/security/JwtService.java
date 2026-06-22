package com.scaler.dbmshow.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(
                jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public JwtUserDto validate(String token) {

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            JwtUserDto response = new JwtUserDto();

            Date expiration = claimsJws.getPayload().getExpiration();
            Long userId = claimsJws.getPayload().get("user_id", Long.class);
            String email = claimsJws.getPayload().get("email", String.class);
            List<String> roles = claimsJws.getPayload().get("roles", List.class);
            response.setRole(new HashSet<>(roles));
            response.setUserId(userId);
            response.setEmail(email);


            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
