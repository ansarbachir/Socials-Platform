/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.dto;
 
import java.util.Map;
 import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class PostCreateProducer{
private String content;
private Map<String,String> files;
private long userID ;

public boolean isPostNull(){
    return this.content == null ||"".equals(content) || userID ==0 ;
}
}

