/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.kafka;

import com.ansarbachir.application.dto.PostCreateProducer;
import com.ansarbachir.application.dto.PostCreateRequest;
import com.ansarbachir.application.dto.PostUpdate;
import com.ansarbachir.application.exceptionHandller.CustomizedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ansar
 */
@Service
@RequiredArgsConstructor
public class PostEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.post.create}")
    private String post_create_topic_name;

    @Value("${kafka.topic.post.update}")
    private String post_update_topic_name;

    public void createPost(PostCreateRequest post, long userId) throws JsonProcessingException {
        if(post.isPostNull()) return;
        
        Map<String,String> mediaList = new HashMap<>();
        
        if(!post.getFiles().isEmpty()){
           for (MultipartFile file : post.getFiles()) {
                if (!file.isEmpty()) {
                    try {
                        byte[] bytes = file.getBytes();
                        String base64 = Base64.getEncoder().encodeToString(bytes);
                        String fileName = file.getOriginalFilename();
                        mediaList.put(fileName,base64);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        throw new CustomizedException("Error during parsing files");
                    }
                }
            }
        }
        kafkaTemplate.send(post_create_topic_name, PostCreateProducer.builder()
                .content(post.getContent())
                .files(mediaList)
                .userID(userId)
                .build());
    }

    
    
    
    
    public void updatePost(PostUpdate post, long userId) throws JsonProcessingException {
        post.setUserID(userId);
        kafkaTemplate.send(post_update_topic_name, post);
    }

}
