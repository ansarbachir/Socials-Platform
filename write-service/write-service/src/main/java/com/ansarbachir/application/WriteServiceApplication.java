package com.ansarbachir.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ansarbachir.application.*"})
@EnableKafka
public class WriteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WriteServiceApplication.class, args);
	}
 
 
        
}
