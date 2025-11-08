/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.controllers;

import com.ansarbachir.application.Services.PostService;
import com.ansarbachir.application.dto.PostApprouve;
import com.ansarbachir.application.dto.PostCreate;
import com.ansarbachir.application.dto.PostUpdate;
import com.ansarbachir.application.exceptionHandller.CustomizedException;
import com.ansarbachir.application.kafka.PostEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class PostController {
 
    
   private final PostEventProducer postEventProducer;
   
   private final PostService postService ;
 
  
    
    @PostMapping("/user/create")
    public ResponseEntity<Object> createPost(@RequestHeader(name="X_User_Id", required = true) String userIdString, 
            @RequestBody PostCreate req) throws JsonProcessingException {
        if(req.isPostNull()){
return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body( "Post Null Not Allowed");
}

if(userIdString == null){
return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( "Unauthorized operation");
        }
        
        postEventProducer.createPost(req,Long.parseLong(userIdString));
        return  ResponseEntity.status(HttpStatus.CREATED).body("Your post has been created and is awaiting admin approval.");
    }
     
    
    
    @PutMapping("/user/update")
    public ResponseEntity<Object> updatePost(@RequestHeader(name="X_User_Id", required = true) String userIdString, 
            @RequestBody PostUpdate req) throws JsonProcessingException {
        if(req.isPostNull()){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body( "Post Null Not Allowed");
        }
        
        if(userIdString == null){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( "Unauthorized operation");
        }
        
        postEventProducer.updatePost(req,Long.parseLong(userIdString));
        
        return  ResponseEntity.status(HttpStatus.CREATED).body("Post updated successfully, awaiting re-approval.");
      
    }
     
    
    @PostMapping("/admin/approuve")
    public ResponseEntity<Object> approuvePost(@RequestHeader(name="X_User_Id", required = true) String userIdString, 
            @RequestBody PostApprouve req) {
        
        System.out.println("------------------>here");
        
        if(req  == null){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post Null Not Allowed");
        }
        
        if(userIdString == null){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized operation");
        }
        
        postService.approuvePost(req,Long.parseLong(userIdString));
        return  ResponseEntity.status(HttpStatus.CREATED).body("Post approved.");
    }
  
}
