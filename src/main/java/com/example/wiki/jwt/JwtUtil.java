package com.example.wiki.jwt;

import com.example.wiki.entity.DTO.UserDTO;
import com.example.wiki.entity.User;
import com.example.wiki.response.AuthResponse;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {

    @Value("${ACCESS_TOKEN_SECRET}")
    private String access_token_secret;
    @Value("${ACCESS_TOKEN_EXPIRATION}")
    private String access_token_expiration;

    @Value("${REFRESH_TOKEN_SECRET}")
    private String refresh_token_secret;
    @Value("${REFRESH_TOKEN_EXPIRATION}")
    private String refresh_token_expiration;


    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        long expirationSeconds = Long.parseLong(access_token_expiration);
        Date creationDate = new Date();
        Date expirationDate = new Date(creationDate.getTime() + expirationSeconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, access_token_secret.getBytes())
                .compact();
    }

    public String generateRefreshToken(User user) {
        long expirationSeconds = Long.parseLong(refresh_token_expiration);
        Date creationDate = new Date();
        Date expirationDate = new Date(creationDate.getTime() + expirationSeconds * 1000);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, refresh_token_secret.getBytes())
                .compact();
    }

    public AuthResponse generateTokens(User user) {
        return new AuthResponse(generateAccessToken(user), generateRefreshToken(user), new UserDTO(user));
    }

    public String getSubjectFromToken(String token) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(access_token_secret.getBytes()).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            throw new Exception("Ошибка валидации токена");
        }
    }


    public void validateToken(String authToken) throws JwtException {
        try {
            Jwts.parser().setSigningKey(access_token_secret.getBytes()).parseClaimsJws(authToken).getBody().get("Authorities");
        } catch (JwtException e) {
            throw new JwtException("Ошибка валидации токена");
        }
    }


}