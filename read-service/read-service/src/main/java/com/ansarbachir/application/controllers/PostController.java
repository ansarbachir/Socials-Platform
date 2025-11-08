/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.controllers;

 import com.ansarbachir.application.Services.PostService;
import com.ansarbachir.application.dto.PostDTO;
import com.ansarbachir.application.exceptionHandller.CustomizedException;
import java.util.List;
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
public class PostController {
  
   
   private final PostService postService ;
 
  
    
    @GetMapping("/user/myposts")
    public ResponseEntity<Object> getMyPosts(@RequestHeader(name="X_User_Id", required = true) String userIdString){
        if(userIdString == null || "".equals(userIdString)){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomizedException("Unauthorized operation"));
        }
       List<PostDTO> myPosts = postService.getMyPosts(Long.parseLong(userIdString));
        return  ResponseEntity.status(HttpStatus.OK).body(myPosts);
    }
   
    @GetMapping("/user/feed")
    public ResponseEntity<Object> getPosts(@RequestHeader(name="X_User_Id", required = true) String userIdString){
        if(userIdString == null || "".equals(userIdString)){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomizedException("Unauthorized operation"));
        }
        List<PostDTO>  posts = postService.getPosts(Long.parseLong(userIdString));
        return  ResponseEntity.status(HttpStatus.OK).body(posts);
    }
     
    
    @GetMapping("/admin/unserapproval")
    public ResponseEntity<Object> getUnderApprovalPosts(@RequestHeader(name="X_User_Id", required = true) String userIdString){
        if(userIdString == null || "".equals(userIdString)){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomizedException("Unauthorized operation"));
        }
        List<PostDTO>  posts = postService.getUnderApprovalPosts();
        return  ResponseEntity.status(HttpStatus.OK).body(posts);
    }
     
    
     
}
