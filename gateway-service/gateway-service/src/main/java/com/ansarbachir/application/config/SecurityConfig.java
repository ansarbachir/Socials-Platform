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
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

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
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {

        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(auth -> auth.anyExchange().permitAll())
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable);

        return http.build();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        
        // Disable CORS
        //httpSecurity.cors(corsConfig -> corsConfig.disable());
        
        // Disable CSRF
        httpSecurity.csrf(csrf -> csrf.disable());
        
        // HTTP Request Filter
        httpSecurity.authorizeHttpRequests(
        requestMatcher -> requestMatcher
                            .requestMatchers("/api/v1/auth/**").permitAll()
                            .requestMatchers("/api/v1/write/**").hasAuthority("USER")
                            .requestMatchers("/api/v1/read/**").hasAuthority("ADMIN")
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
