/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.controllers;

 import com.ansarbachir.application.Services.PostService;
import com.ansarbachir.application.dto.PostsPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<PostsPageResponse> getMyPosts(@RequestHeader(name="X_User_Id", required = true) String userIdString, Pageable pageable){
        if(userIdString == null || "".equals(userIdString)){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
       PostsPageResponse myPosts = postService.getMyPosts(Long.parseLong(userIdString), pageable);
        return  ResponseEntity.status(HttpStatus.OK).body(myPosts);
    }
   
    @GetMapping("/user/feed")
    public ResponseEntity<PostsPageResponse> getPosts(@RequestHeader(name="X_User_Id", required = true) String userIdString, Pageable pageable){
        if(userIdString == null || "".equals(userIdString)){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        PostsPageResponse  posts = postService.getPosts(Long.parseLong(userIdString), pageable);
        return  ResponseEntity.status(HttpStatus.OK).body(posts);
    }
     
    
    @GetMapping("/admin/unserapproval")
    public ResponseEntity<PostsPageResponse> getUnderApprovalPosts(@RequestHeader(name="X_User_Id", required = true) String userIdString,Pageable pageable){
        if(userIdString == null || "".equals(userIdString)){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        PostsPageResponse  posts = postService.getUnderApprovalPosts(pageable);
        return  ResponseEntity.status(HttpStatus.OK).body(posts);
    }
     
    
     
}
