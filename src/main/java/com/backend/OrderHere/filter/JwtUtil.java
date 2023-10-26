package com.backend.OrderHere.filter;

import com.backend.OrderHere.config.StaticConfig;
import com.backend.OrderHere.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

import javax.mail.Message;
import java.security.Key;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Date;

@RequiredArgsConstructor
public class JwtUtil {

    //generate Jwt token based on Authentication(username and password)
    public static String generateToken(Authentication authResult) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .claim("code", "EmptyString")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusMillis(24*60*60*1000))) //24 hours expiration
                .signWith(Keys.hmacShaKeyFor(StaticConfig.JwtSecretKey.getBytes()))
                .compact();
    }

    //generate Jwt token based on Generated code for reset password
    public static String generateToken(String code){
        return Jwts.builder()
                .setSubject(code)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusMillis(24*60*60*1000))) //24 hours expiration
                .signWith(Keys.hmacShaKeyFor(StaticConfig.JwtSecretKey.getBytes()))
                .compact();
    }

    //verify Jwt expiration
    public static boolean checkExpirationTime(String jwtToken) {
        if(jwtToken == null){
            return false;
        }
        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(StaticConfig.JwtSecretKey.getBytes()))
                    .build()
                    .parseClaimsJws(jwtToken);

            //check Expiration
            return claimsJws.getBody().getExpiration().after(new Date());
        }catch (Exception e){
            return false;
        }
    }





}
