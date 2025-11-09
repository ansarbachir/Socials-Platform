/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.dto;
 
import com.ansarbachir.application.Entities.PostMedia;
import java.util.List;
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
public class PostCreateConsumer{
private String content;
private List<PostMedia> mediaList;
private long userID ;

public boolean isPostNull(){
    return this.content == null ||"".equals(content) || userID ==0 ;
}
}

