Social Media Platform – Event-Driven Microservices with Kafka

This repository contains a social media backend built using a microservices architecture with Spring Boot, Kafka, PostgreSQL, and Flyway.
The system follows an event-driven workflow where every post created by a user must be approved by an admin before becoming public.
Each service is responsible for a specific part of the workflow, and Kafka is used for asynchronous communication between them.

✅ Main Features
🔹 1. Create Post (User)
🔹 2. Update Post (User)
🔹 3. Admin Approval Workflow


✅ How to Run
- Clone the repository.
- Run Docker Compose:

         docker compose -f docker-compose.yml up -d

- This will start Kafka, Zookeeper, and PostgreSQL in Docker.
- Start each microservice separately:
    * write-service
    * read-service
    * gateway-service
    * eureka-service
    * auth-service

- Use Postman or your frontend application to create posts and test the admin approval workflow.
