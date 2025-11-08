/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ansarbachir.application.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ansar
 */
@Getter
@Setter
public class UserDTO {
    private long userID;
    private String username;
    private List<String> roles;
}
