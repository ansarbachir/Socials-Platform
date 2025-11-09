/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.kafka;

import com.ansarbachir.application.Entities.PostMedia;
import com.ansarbachir.application.Services.PostService;
import com.ansarbachir.application.dto.PostCreateConsumer;
import com.ansarbachir.application.dto.PostCreateProducer;
 import com.ansarbachir.application.dto.PostUpdate;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ansar
 */

@Service
@RequiredArgsConstructor
public class PostEventConsumer {
    
    private final PostService postService;
     
    private final Path storagePath;
   
    @KafkaListener(topics = "create-post-topic", groupId = "debug-group-1")
    public void create(PostCreateProducer post) {
        if(post.isPostNull())return;
        
         List<PostMedia> mediaList = new ArrayList<>();
        if (post.getFiles() != null && !post.getFiles().isEmpty()) {
            
            post.getFiles().forEach((fileName,data) ->{
                Path filePath = storagePath.resolve(fileName);
                try {
                    Files.write(filePath, data.getBytes());
                    PostMedia media  = new PostMedia();
                    media.setMediaUrl(filePath.toString());
                    mediaList.add(media); 
                } catch (IOException ex) {
                    Logger.getLogger(PostEventConsumer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
        
        }
        
        System.out.println("-----------media list "+mediaList.size());
        
        postService.save(PostCreateConsumer.builder()
                .content(post.getContent())
                .mediaList(mediaList)
                .userID(post.getUserID())
                .build());
    }
    
    
    
    
    
    @KafkaListener(topics = "update-post-topic", groupId = "debug-group-1")
    public void update(PostUpdate post) {
        if(post == null)return;
        if(post.getUserID() == 0) return;
        
        List<String> storedPaths = new ArrayList<>();
        // if attatchement exists
        if (post.getFiles() != null && !post.getFiles().isEmpty()) {
            for (MultipartFile file : post.getFiles()) {
                if (!file.isEmpty()) {
                    Path filePath = storagePath.resolve(file.getOriginalFilename());
                    try {
                        Files.write(filePath, file.getBytes());
                    } catch (IOException ex) {
                        Logger.getLogger(PostEventConsumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    storedPaths.add(filePath.toString()); 
                }
            }
        }
        post.setUrls(storedPaths);
        
        postService.update(post);
    }
    
}
