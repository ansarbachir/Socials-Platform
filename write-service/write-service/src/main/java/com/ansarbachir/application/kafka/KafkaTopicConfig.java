/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 *
 * @author ansar
 */

@Configuration
public class KafkaTopicConfig {
 
    @Value("${kafka.topic.post.create}")
    private String post_create_topic_name;
    
    @Value("${kafka.topic.post.update}")
    private String post_update_topic_name;
    
    @Value("${kafka.topic.post.partitions}")
    private int post_topic_number_partitions;
    
    @Value("${kafka.topic.post.replicas}")
    private int post_topic_replicas;
    
    @Bean
    public NewTopic postCreateTopic() {
        return TopicBuilder.name(post_create_topic_name)
                .partitions(post_topic_number_partitions)
                .replicas(post_topic_replicas)
                .build();
    }
  
    @Bean
    public NewTopic postUpdateTopic() {
        return TopicBuilder.name(post_update_topic_name)
                .partitions(post_topic_number_partitions)
                .replicas(post_topic_replicas)
                .build();
    }
}
