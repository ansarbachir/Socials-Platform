/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.config;
 
 
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author ansar
 */
@EnableWebFluxSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
   
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // Disable CSRF
        httpSecurity.csrf(csrf -> csrf.disable());
        
        // HTTP Request Filter
        httpSecurity.authorizeHttpRequests(
        requestMatcher -> requestMatcher
                            .requestMatchers("/api/v1/auth/**").permitAll()
                            .anyRequest().authenticated()
        );
                             
        // Set Session  policy = STATELESS
        httpSecurity.sessionManagement(
        sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Add JWT Authentication Filter
        httpSecurity.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
                
        return httpSecurity.build();
    }
}
