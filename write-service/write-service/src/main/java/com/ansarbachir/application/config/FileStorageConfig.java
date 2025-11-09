/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ansar
 */
@Configuration
public class FileStorageConfig {

    @Value("${app.file.storage-location}")
    private String storageLocation;

    @Bean
    public Path fileStoragePath() {
        Path path = Paths.get(storageLocation).toAbsolutePath().normalize();

        try {
            Files.createDirectories(path);  
        } catch (IOException e) {
            throw new RuntimeException("Could not create storage directory: " + storageLocation, e);
        }

        return path;
    }
}
