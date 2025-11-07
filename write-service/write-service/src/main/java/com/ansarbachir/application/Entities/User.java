/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.Entities;

/**
 *
 * @author ansar
 */
 

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="user_name",nullable = false, unique = true)
    @Size(max = 150)
    private String username;

    @Column(name ="user_email",nullable = false, unique = true)
    @Size(max = 200)
    private String email;

    @Column(name ="user_password", nullable = false)
    @Size(max = 200)
    private String password;

    @Column(name ="image_url", nullable = true)
    @Size(max = 200)
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt ;
}

