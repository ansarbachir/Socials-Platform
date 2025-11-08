/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.kafka;

/**
 *
 * @author ansar
 */
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class KafkaDebugConfig {

    @Autowired(required = false)
    private KafkaListenerEndpointRegistry registry;

    @Bean
    public ApplicationRunner kafkaDebugRunner() {
        return args -> {
            System.out.println("----- KAFKA DEBUG START -----");
            if (registry == null) {
                System.out.println("KafkaListenerEndpointRegistry = NULL -> @EnableKafka absent ou spring-kafka non présent");
            } else {
                System.out.println("Listeners count: " + registry.getListenerContainerIds().size());
                registry.getListenerContainerIds().forEach(id -> {
                    var c = registry.getListenerContainer(id);
                    System.out.println("Listener id: " + id + " , isRunning=" + (c != null && c.isRunning()));
                });
            }
            System.out.println("----- KAFKA DEBUG END -----");
        };
    }
}
