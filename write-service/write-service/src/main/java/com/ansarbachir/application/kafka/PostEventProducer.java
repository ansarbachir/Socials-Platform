/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.kafka;



import com.ansarbachir.application.dto.PostCreate;
import com.ansarbachir.application.dto.PostUpdate;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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
    
   
     
    public void createPost(PostCreate post, long userId) throws JsonProcessingException {
        post.setUserID(userId);
        kafkaTemplate.send(post_create_topic_name, post);
    }

    
    
    public void updatePost(PostUpdate post, long userId) throws JsonProcessingException {
         post.setUserID(userId);
        kafkaTemplate.send(post_update_topic_name, post);
    }
  

     
}
