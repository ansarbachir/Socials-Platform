package com.ansarbachir.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ansarbachir.application.*"})
public class WriteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WriteServiceApplication.class, args);
	}

}
