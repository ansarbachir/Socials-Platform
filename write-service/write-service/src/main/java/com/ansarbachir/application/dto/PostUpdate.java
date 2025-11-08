/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ansar
 */
 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdate{
    long idPost;
    private String content;
    private List<MultipartFile> files;
    private List<String> urls;
    private long userID ;
    private boolean isApprouved=false;
    
    public boolean isPostNull(){return this.idPost ==0 || this.content == null ||"".equals(content) ;}
}