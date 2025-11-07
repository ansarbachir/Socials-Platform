/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author ansar
 */
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret}")
    private String base64Secret;
    @Value("${jwt.issuer}")
    private String ISSUER ;
    @Value("${jwt.type}")
    private String ACCESSTOKEN;

   
    public boolean validateToken(String jwtToken) {
        return parseToken(jwtToken).isPresent();
    }

    public  Optional<String> getUsernameFromToken(String jwtToken) {

        var claimsOptional = parseToken(jwtToken);


        if (claimsOptional.isPresent()) {
            return Optional.of(claimsOptional.get().getSubject());
        }
        return Optional.empty();
    }

    private   Optional<Claims> parseToken(String jwtToken) {
  
        byte[] secretBytes = Base64.getDecoder().decode(base64Secret);
        SecretKeySpec key = new SecretKeySpec(secretBytes, "HmacSHA256");
        Jws<Claims> jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken);

        try {
            Claims claims = jwtParser.getBody();

            String typeClaim = (String) claims.get("type");

            if (!typeClaim.equals(ACCESSTOKEN)) {
                throw new Exception("Access denied - Wrong type token");
            }
            // Check token expiration
            Date expiration = claims.getExpiration();
            Date now = new Date();

            if (expiration != null && expiration.before(now)) {
                // Token has expired
                log.error("JWT expired at: {}. Current time: {}. Token: {}",
                        expiration, now, jwtToken);
                // You can throw an exception, log, or handle expired token as needed
                throw new ExpiredJwtException(null, claims, "JWT expired " + expiration);
            }

            // Token is valid, proceed with processing
            return Optional.of(claims);

        } catch (ExpiredJwtException ex) {
            // Handle expired token exception
            log.error("Expired JWT Exception occurred: {}", ex.getMessage());
            // You can log, send an error response, or perform token renewal
        } catch (JwtException | IllegalArgumentException e) {
            // Handle other JWT exceptions or invalid token format
            log.error("JWT Exception occurred: {}", e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(JwtUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Optional.empty();
    }
 
    
    public String generateToken(String subject,boolean isAdmin) {
        byte[] secretBytes = Base64.getDecoder().decode(base64Secret);
        SecretKey key = Keys.hmacShaKeyFor(secretBytes);

        Instant now = Instant.now();
        Date issuedAt = Date.from(now);
        Date expiresAt = Date.from(now.plusSeconds(900)); // 15 minutes
        //Define ROLE
        String[] roles; 
        if(isAdmin){
        roles = new String[]{"ROLE_ADMIN"};
        }else{
        roles = new String[]{"ROLE_USER"};
        }
        
        String jwt = Jwts.builder()
            .setIssuer(ISSUER)
            .setSubject(subject)                          
            .setIssuedAt(issuedAt)
            .setExpiration(expiresAt)
            .setClaims(Map.of("roles",roles))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        return jwt;
    }
}
