/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.config;

 
import com.ansarbachir.application.dto.UserDTO;
import org.springframework.web.server.WebFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
 import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 *
 * @author ansar
 */
@Component
@RequiredArgsConstructor
public class JwtValidationWebFilter implements WebFilter {

    private final WebClient webClient;
  

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        
        
        String path = exchange.getRequest().getURI().getPath();
        
         
        if (path.startsWith("/api/v1/auth")) {
        return chain.filter(exchange);
        }
        
        String token = extractToken(exchange);
        if (token == null) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing token"));
        }
        
        return webClient.post()
                .uri("http://localhost:7676/validate")
                .bodyValue(token)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .flatMap(user -> {
                    if (user != null) {
                       List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList();

                        UsernamePasswordAuthenticationToken authToken
                                = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);

                       
                    ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(r -> r.header("X_User_Id", String.valueOf(user.getUserID())))
                            .build();
                    
                        String s = mutatedExchange.getRequest().getHeaders().getFirst("X_User_Id");
                        
                         return chain.filter(mutatedExchange) 
            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(
                    Mono.just(new SecurityContextImpl(authToken))));
                        
                    } else {
                        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
                    }
                })
                  .onErrorResume(e -> {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            });
    }

    private String extractToken(ServerWebExchange exchange) {
        List<String> authHeaders = exchange.getRequest().getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION);
        if (authHeaders.isEmpty()) return null;

        String bearer = authHeaders.get(0);
        if (bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
 
}
