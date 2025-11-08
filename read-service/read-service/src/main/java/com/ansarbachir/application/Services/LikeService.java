/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ansarbachir.application.Services;

import com.ansarbachir.application.Repositories.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author ansar
 */
@Service
@RequiredArgsConstructor
public class LikeService {

    private LikeRepository likeRepository;
    
}
