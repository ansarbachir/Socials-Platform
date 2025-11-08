/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ansar
 */
 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostApprouve{
    long idPost;
    private String content ;   
    private boolean isApprouved=false;
    
    public boolean isPostNull(){return this.idPost ==0 || this.content == null ||"".equals(content) ;}
}