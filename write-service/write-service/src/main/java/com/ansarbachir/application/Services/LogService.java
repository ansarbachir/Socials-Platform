/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ansarbachir.application.Services;

import com.ansarbachir.application.Entities.Log;
import com.ansarbachir.application.Entities.User;
import com.ansarbachir.application.Repositories.LogRepository;
import com.ansarbachir.application.Repositories.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
 
/**
 *
 * @author ansar
 */
@Service
@RequiredArgsConstructor
public class LogService {

    private UserRepository userRepository;
    private LogRepository logRepository;
    
    
    public boolean save(String content, String title, long userID){
        Log log = new Log();
        log.setContent(content);
        log.setTitle(title);
        Optional<User> user = userRepository.findById(userID);
        if(user.isEmpty())return false;
        log.setUser(user.get());
        this.logRepository.save(log);
        return true;
    }
    }
