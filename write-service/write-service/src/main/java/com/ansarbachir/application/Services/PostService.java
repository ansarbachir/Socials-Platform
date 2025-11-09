/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ansarbachir.application.Services;

import com.ansarbachir.application.Entities.Log;
import com.ansarbachir.application.Entities.Post;
import com.ansarbachir.application.Entities.User;
import com.ansarbachir.application.Repositories.LogRepository;
import com.ansarbachir.application.Repositories.PostRepository;
import com.ansarbachir.application.Repositories.UserRepository;
import com.ansarbachir.application.dto.PostApprouve;
import com.ansarbachir.application.dto.PostCreateConsumer;
 import com.ansarbachir.application.dto.PostUpdate;
import com.ansarbachir.application.enums.PostStatusEnum;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ansar
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final LogRepository logRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    
    
    @Transactional
    public void save(PostCreateConsumer req) {

        if(req.isPostNull()) return;
        
        User user = userRepository.findById(req.getUserID())
            .orElseThrow(() -> new RuntimeException("User Not Found"));
        
        Post post = new Post();
        
        if(!req.getMediaList().isEmpty()){
        req.getMediaList().forEach(m -> {m.setPost(post);});
        }
        post.setMediaList(req.getMediaList());
        
        post.setContent(req.getContent());
        post.setUser(user);                       
        post.setStatus(PostStatusEnum.UNDER_APPROVAL);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.saveAndFlush(post);

        
        
        
        Log log = new Log();
        log.setTitle("Create post");
        log.setContent(req.getContent());
        log.setCreatedAt(LocalDateTime.now());
        log.setUser(user);                          
        logRepository.save(log);
    }
    
    
    @Transactional
    public void update(PostUpdate req) {
        User user = userRepository.findById(req.getUserID()).orElseThrow(() -> new RuntimeException("User Not Found"));
        Post post = postRepository.findById(req.getIdPost()).orElseThrow(() -> new RuntimeException("Post Not Found"));
        
        
        post.setContent(req.getContent());
        //post.setMediaUrls(req.getUrls());
        //post.setModifiedAt(LocalDateTime.now()); //   i need to add this  field
        post.setStatus(PostStatusEnum.UNDER_APPROVAL);
        postRepository.save(post);
        

        Log log = new Log();
        log.setTitle("Update post");
        log.setContent(req.getContent());
        log.setCreatedAt(LocalDateTime.now());
        log.setUser(user);                          
        logRepository.save(log);
    }
    
    
    @Transactional
    public void approuvePost(PostApprouve req, long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Post post = postRepository.findById(req.getIdPost()).orElseThrow(() -> new RuntimeException("Post Not Found"));
     
        if(req.isApprouved()){
            post.setStatus(PostStatusEnum.APPROVE);
        }else{
            post.setStatus(PostStatusEnum.Reject);    
        }
        postRepository.save(post);
        

        Log log = new Log();
        log.setTitle("Approuve post");
        log.setContent(post.getStatus().getStatus());
        log.setCreatedAt(LocalDateTime.now());
        log.setUser(user);                          
        logRepository.save(log);
        
    }
    
    
}
