/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ansarbachir.application.Services;

 import com.ansarbachir.application.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author ansar
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;
    
}
