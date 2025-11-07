/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.controllers;

import com.ansarbachir.application.Services.UserService;
import com.ansarbachir.application.config.JwtUtils;
import com.ansarbachir.application.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ansar
 */

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class TestController {
 
    
    private final UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
       
        if(request.isRequestNull() ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        
        System.out.println(request.email()+"  "+request.password());
        
        var jwtToken = userService.login(request);
        
        if(jwtToken == null || jwtToken.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }
   
   
}
