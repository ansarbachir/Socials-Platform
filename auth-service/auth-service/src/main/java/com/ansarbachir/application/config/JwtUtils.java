/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.config;

import com.ansarbachir.application.Entities.Role;
import com.ansarbachir.application.Entities.User;
import com.ansarbachir.application.Repositories.UserRepository;
import com.ansarbachir.application.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author ansar
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private String base64Secret;
    @Value("${jwt.issuer}")
    private String ISSUER ;
    @Value("${jwt.type}")
    private String ACCESSTOKEN;

   private final UserRepository userRepository;
    
    public boolean validateToken(String jwtToken) {
        try {
        Claims claims = parseToken(jwtToken);
        if(claims == null) return false;
        return claims.getExpiration().after(new Date());
    } catch (ExpiredJwtException e) {
        System.out.println("Token expired: " + e.getMessage());
        return false;
    } catch (Exception e) {
        return false;
    }
    }

   
    
    public String generateToken(String subject,List<Role> roles) {
        byte[] secretBytes = Base64.getDecoder().decode(base64Secret);
        SecretKey key = Keys.hmacShaKeyFor(secretBytes);

        Instant now = Instant.now();
        Date issuedAt = Date.from(now);
        Date expiresAt = Date.from(now.plusSeconds(900)); // 15 minutes
        
        String jwt = Jwts.builder()
            .setIssuer(ISSUER)
            .setSubject(subject)                          
            .setIssuedAt(issuedAt)
            .setExpiration(expiresAt)
            .claim("roles", roles.stream().map(Role::getName).toList())
            .claim("type", ACCESSTOKEN)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        return jwt;
    }
    
    
     public Claims parseToken(String token) {
        if(token == null ||"".equals(token)) return null;
        byte[] secretBytes = Base64.getDecoder().decode(base64Secret);
        SecretKeySpec key = new SecretKeySpec(secretBytes, "HmacSHA256");
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    public  String getUsername(String token) {
        Claims claims = parseToken(token);
        if(claims == null) return null;
        return claims.getSubject(); 
    }

    public  List<String> getRoles(String token) {
        Claims claims = parseToken (token);
        if(claims == null) return null;
        Object rolesClaim = claims.get("roles");
        List<String> roles = new ArrayList<>();
        if (rolesClaim instanceof List<?>) {
            for (Object role : (List<?>) rolesClaim) {
                roles.add(role.toString());
            }
        }
        return roles;
    }
    
    public UserDTO getUsernameAndRoles(String token){
         Claims claims = parseToken(token);

         if(claims == null) return null;
         
         
        String email = claims.getSubject();
        Object rolesClaim = claims.get("roles");
        List<String> roles = new ArrayList<>();
        if (rolesClaim instanceof List<?> list) {
            for (Object role : list) {
                roles.add(role.toString());
            }
        }
 
        
        Optional<User> optionalUser = userRepository.findByemail(email);
        if(optionalUser.isEmpty()){
            return null;
        }
        
        UserDTO user = new UserDTO();
        user.setUserID(optionalUser.get().getId());
        user.setUsername(email);
        user.setRoles(roles);
        return user;
    }
}
