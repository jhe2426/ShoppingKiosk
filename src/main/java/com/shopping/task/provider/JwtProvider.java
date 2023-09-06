package com.shopping.task.provider;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    public String create(String id, String role) {
        
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String jwt = 
            Jwts.builder()
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .setSubject(id)
                    .setIssuedAt(new Date())
                    .setExpiration(expiredDate)
                    .claim("id", id)
                    .claim("role", role)
                    .compact();
        return jwt;
    }


    public AuthToken validate(String jwt) {

        String id = null;
        String role = null;

        try {
            Claims claims =
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody();

            id = (String) claims.get("id");
            role = (String) claims.get("role");
            
        } catch (Exception exception) {
            return null;
        }



        return new AuthToken(id, role);
    }
    
}