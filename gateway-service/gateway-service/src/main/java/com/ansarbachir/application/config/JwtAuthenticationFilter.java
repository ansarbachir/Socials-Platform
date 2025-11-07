/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.config;

 
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author ansar
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    
    private final UserDetailsService userDetailsService;
    private final JwtUtils  jwtUtils;
    
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
             @NonNull HttpServletResponse response,
             @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        
        
        
        // Log the request information
        log.info("Request received: " + request.getRemoteAddr() + " - " +
                 request.getMethod() + " " +  request.getRequestURI());
          
        // Check if the request URI starts with "/api/v1/"
        if (!requestURI.startsWith("/api/v1/")) {
            // Block the request
             log.warn("Block access other url");
            response.sendError(403,"Access Forbidden");
            return;
        }
        
        
        // Fetch token from request
        Optional<String> jwtTokenOptional = getTokenFromRequest(request);
        
        // Validate jwt token ->  J>T Utils
        jwtTokenOptional.ifPresent(jwtToken ->{
            if(jwtUtils.validateToken(jwtToken)){
                
                // Get Username from JWT Token
                var usernameOptional = jwtUtils.getUsernameFromToken(jwtToken);
                
                usernameOptional.ifPresent(username ->{
                    
                // Fetch user details with the help of username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                  // Check user roles
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                 
                // Create Authentication token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Set authentication token to Security Context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                
                    try {
                        //Pass request and response to next filter
                        filterChain.doFilter(request, response);
                    } catch (IOException | ServletException ex) {
                        Logger.getLogger(JwtAuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
              
            }
        });
        
        //Pass request and response to next filter
        filterChain.doFilter(request, response);
         
    }
    
 

    private Optional<String> getTokenFromRequest(HttpServletRequest request){
        // Extract Authentication header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        // Bearer <JWT TOKEN>
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            return Optional.of(authHeader.substring(7));
        }
        
        return Optional.ofNullable(null);
    }
}
