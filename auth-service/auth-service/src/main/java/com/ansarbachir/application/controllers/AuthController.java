/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.controllers;

import com.ansarbachir.application.Services.UserService;
import com.ansarbachir.application.dto.LoginRequestDto;
import com.ansarbachir.application.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
/**
 *
 * @author ansar
 */

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {
 
    
    private final UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
        if(request.isRequestNull() ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        var jwtToken = userService.login(request);
        if(jwtToken == null || jwtToken.isEmpty()){
            data.put("success",false);
            data.put("message","Unauthorized");
            response.put("data",data);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString());
        }
        data.put("success",true);
        data.put("token",jwtToken);
        response.put("data",data);
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }
   
    
    
    @PostMapping("/validate")
    public ResponseEntity<UserDTO> validate(@RequestBody String token) {
        if(token == null || "".equals(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        var user = userService.validateToken(token);
        if(user == null){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else{
        return ResponseEntity.status(HttpStatus.OK).body(user);
        }
      
    }
   
}
