package com.example.wiki.jwt;

import com.example.wiki.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expirationTime;

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException expEx) {
            System.out.println("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            System.out.println("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            System.out.println("Malformed jwt");
        } catch (SignatureException sEx) {
            System.out.println("Invalid signature");
        } catch (Exception e) {
            System.out.println("invalid token");
        }
        return false;
    }

    public String getSubjectFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


    public String generateToken(User my_user) {
//        HashMap<String, Object> claims = new HashMap<>();

        long expirationSeconds = Long.parseLong(expirationTime);
        Date creationDate = new Date();
        Date expirationDate = new Date(creationDate.getTime() + expirationSeconds * 1000);

        return Jwts.builder()
//                .setClaims(claims)
                .setSubject(my_user.getLogin())
                .setIssuedAt(creationDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
}