/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.controllers;

import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ansar
 */

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class TestController {
 
    
   
    @GetMapping("/test")
    public String testWrite() {
        return "Write service OK ✅";
    }
    
 

@GetMapping("/headers")
    public ResponseEntity<String> getHeaders(@RequestHeader Map<String, String> headers) {
        // Logue tous les headers
        headers.forEach((k, v) -> System.out.println(k + " : " + v));

        // Vérifie si le header spécifique est présent
        String key = headers.get("x-internal-gateway-key");
        return ResponseEntity.ok("X-Internal-Gateway-Key = " + key);
    }
    
}
